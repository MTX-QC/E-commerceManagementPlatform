package com.mtx.mall.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/*
 * 描述：  图片工具类
 * */
public class ImageUtil {
    public static void main(String[] args) throws Exception {
        String path = "D:/1.New start/Java/images/";
        //切割
        Thumbnails.of(path + "1.jpg").sourceRegion(Positions.BOTTOM_RIGHT, 200, 200).size(200, 200).toFile(path+"crop.jpg");

        //缩放
        Thumbnails.of(path+"1.jpg").scale(0.7).toFile(path+"scale1.jpg");
        Thumbnails.of(path+"1.jpg").scale(1.5).toFile(path+"scale2.jpg");
        Thumbnails.of(path+"1.jpg").size(500,500).keepAspectRatio(false).toFile(path+"size1.jpg");
        Thumbnails.of(path+"1.jpg").size(500,500).keepAspectRatio(true).toFile(path+"size2.jpg");

        //旋转
        Thumbnails.of(path+"1.jpg").size(500,500).keepAspectRatio(true).rotate(90).toFile(path+"rotate1.jpg");
        Thumbnails.of(path+"1.jpg").size(500,500).keepAspectRatio(true).rotate(180).toFile(path+"rotate2.jpg");

    }
}
