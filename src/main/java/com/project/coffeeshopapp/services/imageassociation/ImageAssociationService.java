package com.project.coffeeshopapp.services.imageassociation;

import com.project.coffeeshopapp.customexceptions.ImageAlreadyAssociatedException;
import com.project.coffeeshopapp.models.Image;
import com.project.coffeeshopapp.models.contracts.ImageAssociable;
import com.project.coffeeshopapp.repositories.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageAssociationService implements IImageAssociationService {

    private final ImageRepository imageRepository;

    /**
     * Associates images with the given entity.
     *
     * @param entity   The entity to associate images with.
     * @param imageIds List of image IDs to associate.
     * @return List of associated Image entities.
     */
    @Override
    @Transactional
    public <T extends ImageAssociable> List<Image> createImageAssociations(T entity, List<Long> imageIds) {
        if (imageIds == null || imageIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Image> images = imageRepository.findAllById(imageIds);

        for (Image image : images) {
            if (image.getProduct() != null || image.getCategory() != null || image.getUser() != null) {
                throw new ImageAlreadyAssociatedException("Image with ID " + image.getId() + " is already associated with an entity.");
            }

            entity.addImage(image);
        }

        return images;
    }

    /**
     * Updates image associations for the given entity based on the provided image IDs.
     *
     * @param entity   The entity to update image associations for.
     * @param imageIds List of image IDs representing the desired state.
     * @return Updated list of associated Image entities.
     */
    @Override
    @Transactional
    public <T extends ImageAssociable> List<Image> updateImageAssociations(T entity, List<Long> imageIds) {
        if (imageIds == null) {
            // no action is needed
            return entity.getImages();
        }

        Set<Long> newImageIds = new HashSet<>(imageIds);
        Set<Long> existingImageIds = entity.getImages().stream()
                .map(Image::getId)
                .collect(Collectors.toSet());

        // Determine images to add and remove
        Set<Long> imagesToAddIds = new HashSet<>(newImageIds);
        imagesToAddIds.removeAll(existingImageIds);

        Set<Long> imagesToRemoveIds = new HashSet<>(existingImageIds);
        imagesToRemoveIds.removeAll(newImageIds);

        // Associate new images
        if (!imagesToAddIds.isEmpty()) {
            List<Image> imagesToAdd = imageRepository.findAllById(imagesToAddIds);
            for (Image image : imagesToAdd) {
                if (image.getProduct() != null || image.getCategory() != null || image.getUser() != null) {
                    throw new ImageAlreadyAssociatedException("Image with ID " + image.getId() + " is already associated with an entity.");
                }
                entity.addImage(image);
            }
        }

        // Dissociate removed images
        if (!imagesToRemoveIds.isEmpty()) {
            List<Image> imagesToRemove = entity.getImages().stream()
                    .filter(image -> imagesToRemoveIds.contains(image.getId()))
                    .toList();
            for (Image image : imagesToRemove) {
                entity.removeImage(image);
            }
        }

        return entity.getImages();
    }
}

