package com.project.coffeeshopapp.models.contracts;

import com.project.coffeeshopapp.models.Image;

import java.util.List;

public interface ImageAssociable {
    List<Image> getImages();
    void addImage(Image image);
    void removeImage(Image image);
}
