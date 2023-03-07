package com.javatankwar.tankwar;

import javax.swing.*;
import java.awt.*;

public class Tools {//用于加载图片的工具类
    public static Image getImage(String imageName){
        return new ImageIcon("assets/images/"+imageName).getImage();
    }
}
