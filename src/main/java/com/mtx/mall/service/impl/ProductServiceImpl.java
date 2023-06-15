package com.mtx.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mtx.mall.common.Constant;
import com.mtx.mall.exception.MtxMallException;
import com.mtx.mall.exception.MtxMallExceptionEnum;
import com.mtx.mall.model.dao.ProductMapper;
import com.mtx.mall.model.pojo.Product;
import com.mtx.mall.model.query.ProductListQuery;
import com.mtx.mall.model.request.AddProductReq;
import com.mtx.mall.model.request.ProductListReq;
import com.mtx.mall.model.vo.CategoryVO;
import com.mtx.mall.service.CategoryService;
import com.mtx.mall.service.ProductService;
import com.mtx.mall.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * 描述：  商品服务实现类
 * */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryService categoryService;

    /*
     * 添加
     * */
    @Override
    public void add(AddProductReq addProductReq) {
        Product product = new Product();
        BeanUtils.copyProperties(addProductReq, product);
        //检查名字是否重复
        Product productOld = productMapper.selectByName(addProductReq.getName());
        if (productOld != null) {
            throw new MtxMallException(MtxMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.insertSelective(product);
        //判断插入是否成功
        if (count == 0) {
            throw new MtxMallException(MtxMallExceptionEnum.CREATE_FAILED);
        }
    }

    /*
     * 更新
     * */
    @Override
    public void update(Product updateProduct) {
        Product productOld = productMapper.selectByName(updateProduct.getName());

        //同名且不同id，不能继续修改
        if (productOld != null && !productOld.getId().equals(updateProduct.getId())) {
            throw new MtxMallException(MtxMallExceptionEnum.NAME_EXISTED);
        }
        int count = productMapper.updateByPrimaryKeySelective(updateProduct);
        if (count == 0) {
            throw new MtxMallException(MtxMallExceptionEnum.UPDATE_FAILED);
        }
    }

    /*
     * 删除
     * */
    @Override
    public void delete(Integer id) {
        Product productOld = productMapper.selectByPrimaryKey(id);

        //查不到该记录，无法删除
        if (productOld == null) {
            throw new MtxMallException(MtxMallExceptionEnum.DELETE_FAILED);
        }
        int count = productMapper.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new MtxMallException(MtxMallExceptionEnum.DELETE_FAILED);
        }
    }

    /*
     * 后台批量上下架商品
     * */
    @Override
    public void batchUpdateSellStatus(Integer[] ids, Integer sellStatus) {
        productMapper.batchUpdateSellStatus(ids, sellStatus);

    }


    /*
     * 后台商品列表
     * */
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectListForAdmin();
        PageInfo pageInfo = new PageInfo(products);
        return pageInfo;
    }

    /*
     * 前台商品详情
     * */
    @Override
    public Product detail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return product;
    }

    /*
    * 前台商品列表，这个有点难度
    * 实现思路如下：
    1、首先创建一个 ProductListQuery 对象，用于构建查询条件。
    2、对搜索关键字进行处理，如果关键字不为空，则构建模糊搜索的查询条件。
    3、对目录进行处理：如果指定了一个目录ID，需要获取该目录及其所有子目录的ID，构建一个目录ID列表。
    4、对排序进行处理：如果指定了排序方式，则根据指定的排序方式进行分页查询；否则，使用默认的分页查询。
    5、执行商品列表的查询操作，将查询结果保存在 productList 中。
    6、将查询结果封装为 PageInfo 对象，包含了商品列表和分页信息。
    7、返回 PageInfo 对象。
    ***********************************************************************************
    看完代码，再看具体步骤，如下：
    1、创建一个 ProductListQuery 对象，用于构建查询条件。
    2、如果有搜索关键字，将关键字进行处理，添加通配符 %，使其支持模糊匹配，并将处理后的关键字设置到查询条件中。
    3、如果指定了目录ID，需要获取该目录及其所有子目录的ID，构建一个目录ID列表。
        首先调用 categoryService.listCategoryForCustomer 方法获取指定目录的信息及其子目录信息。
        创建一个 ArrayList<Integer> 对象，用于保存目录ID列表，将指定目录的ID添加到列表中。
        调用递归方法 getCategoryIds，传入子目录信息和目录ID列表，递归获取所有子目录的ID，并将其添加到目录ID列表中。
        将目录ID列表设置到查询条件中。
    4、处理排序方式，如果指定了排序方式，则调用 PageHelper.startPage 方法设置分页参数和排序方式；否则，只设置分页参数。
    5、调用 productMapper.selectList 方法执行商品列表的查询操作，将查询结果保存在 productList 中。
    6、创建一个 PageInfo 对象，将查询结果列表和分页信息作为参数传入。
    7、返回 PageInfo 对象，供前台页面展示。
    总体思路是根据传入的查询条件和分页参数进行商品列表的查询，同时支持搜索、目录过滤和排序功能。通过递归获取所有子目录的ID，
    确保查询结果包含了指定目录及其子目录下的商品。最终将查询结果封装为 PageInfo 对象返回，供前台页面展示商品列表和分页导航栏。
    * */
    @Override
    public PageInfo list(ProductListReq productListReq) {
        //构建Query对象
        ProductListQuery productListQuery = new ProductListQuery();

        //搜索处理
        if (!StringUtils.isEmpty(productListReq.getKeyword())) {
            String keyword = new StringBuilder().append("%").append(productListReq.getKeyword()).append("%").toString();
            productListQuery.setKeyword(keyword);
        }

        //目录处理：如果查某个目录下的商品，不仅是需要查出该目录下的，还要把所有子目录的所有商品都查出来，所以要拿到一个目录id的List
        if (productListReq.getCategoryId() != null) {
            List<CategoryVO> categoryVOList = categoryService.listCategoryForCustomer(productListReq.getCategoryId());
            ArrayList<Integer> categoryIds = new ArrayList<>();
            categoryIds.add(productListReq.getCategoryId());
            getCategoryIds(categoryVOList, categoryIds);
            productListQuery.setCategoryIds(categoryIds);
        }

        //排序处理
        String orderBy = productListReq.getOrderBy();
        if (Constant.ProductListOrderBy.PRICE_ORDER_ENUM.contains(orderBy)) {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize(), orderBy);
        } else {
            PageHelper.startPage(productListReq.getPageNum(), productListReq.getPageSize());
        }
        // 查询商品列表
        List<Product> productList = productMapper.selectList(productListQuery);
        // 封装为PageInfo对象
        PageInfo pageInfo = new PageInfo(productList);
        return pageInfo;
    }


    /*
    getCategoryIds 方法是一个递归方法，用于获取指定目录及其子目录的ID，并将其添加到目录ID列表中。
    方法接受两个参数：categoryVOList 是目录的子目录列表，categoryIds 是用于保存目录ID的列表。
    方法通过循环遍历 categoryVOList 中的每个子目录。
    对于每个子目录，首先判断是否为 null。
    如果子目录不为 null，将其 ID 添加到 categoryIds 列表中。
    然后，递归调用 getCategoryIds 方法，传入子目录的子目录列表和 categoryIds 列表，实现对子目录的子目录的遍历。
    通过递归调用，可以遍历所有子目录的子目录，直到遍历完所有层级的子目录。
    最终，所有目录的ID都会被添加到 categoryIds 列表中。
    该方法的目的是获取指定目录及其所有子目录的ID，以便在商品列表查询中使用。通过递归调用，可以处理多层级的目录结构，并确保所有子目录的ID都被添加到列表中。
    * */
    // 递归获取目录及子目录的ID
    private void getCategoryIds(List<CategoryVO> categoryVOList, ArrayList<Integer> categoryIds) {
        for (int i = 0; i < categoryVOList.size(); i++) {
            CategoryVO categoryVO = categoryVOList.get(i);
            if (categoryVO != null) {
                categoryIds.add(categoryVO.getId());
                getCategoryIds(categoryVO.getChildCategory(), categoryIds);
            }
        }
    }


    /*
    * 读取Excel中内容，并写入数据库
    * */
    @Override
    public void addProductByExcel(File destFile) throws IOException {
        List<Product> products = readProductsFromExcel(destFile);
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            Product productOld = productMapper.selectByName(product.getName());
            if (productOld != null){
                throw new MtxMallException(MtxMallExceptionEnum.NAME_EXISTED);
            }
            int count = productMapper.insertSelective(product);
            if (count == 0){
                throw new MtxMallException(MtxMallExceptionEnum.CREATE_FAILED);
            }
        }
    }

    /*
    * 读取Excel中内容
    * */
    private List<Product> readProductsFromExcel(File excelFile) throws IOException {
        ArrayList<Product> listProducts = new ArrayList<>();
        FileInputStream inputStream = new FileInputStream(excelFile);

        //用HSSFWorkbook,后缀为.xls(测试通过)
        //用FSSFWorkbook,后缀为.xlsx(这个测试没通过，一直报错，提示后缀的问题)，
        //使用这个方法需要可客户进行约定
        HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
        HSSFSheet firstSheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = firstSheet.iterator();
        while (iterator.hasNext()){
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            Product aProduct = new Product();

            while (cellIterator.hasNext()){
                Cell nextCell = cellIterator.next();
                int columnIndex = nextCell.getColumnIndex();

                switch (columnIndex){
                    case 0:
                        aProduct.setName((String) ExcelUtil.getCellValue(nextCell));
                        break;
                    case 1:
                        aProduct.setImage((String) ExcelUtil.getCellValue(nextCell));
                        break;
                    case 2:
                        aProduct.setDetail((String) ExcelUtil.getCellValue(nextCell));
                        break;
                    case 3:
                        //数据的一般处理方法
                        Double cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setCategoryId(cellValue.intValue());
                        break;
                    case 4:
                        cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setPrice(cellValue.intValue());
                        break;
                    case 5:
                        cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setStock(cellValue.intValue());
                        break;
                    case 6:
                        cellValue = (Double) ExcelUtil.getCellValue(nextCell);
                        aProduct.setStatus(cellValue.intValue());
                        break;
                    default:
                        break;
                }
            }
            listProducts.add(aProduct);
        }
        workbook.close();
        inputStream.close();
        return listProducts;
    }


}









