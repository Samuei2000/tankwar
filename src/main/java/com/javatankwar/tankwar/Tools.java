package com.javatankwar.tankwar;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;

class Tools {//用于加载图片的工具类
    static Image getImage(String imageName){
        return new ImageIcon("assets/images/"+imageName).getImage();
    }
    static void playAudio(String fileName) {
        Media sound=new Media(new File("assets/audios/"+ fileName).toURI().toString());
        MediaPlayer mediaPlayer=new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
