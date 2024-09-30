package com.project.coffeeshopapp.services.imageassociation;

import com.project.coffeeshopapp.models.Image;
import com.project.coffeeshopapp.models.contracts.ImageAssociable;

import java.util.List;

public interface IImageAssociationService {
    <T extends ImageAssociable> List<Image> createImageAssociations(T entity, List<Long> imageIds);
    <T extends ImageAssociable> List<Image> updateImageAssociations(T entity, List<Long> imageIds);
}
