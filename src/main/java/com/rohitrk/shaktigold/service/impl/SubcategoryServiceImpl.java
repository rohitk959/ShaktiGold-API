package com.rohitrk.shaktigold.service.impl;

import com.rohitrk.shaktigold.dao.SubcategoryDAO;
import com.rohitrk.shaktigold.model.SubCategoryProperty;
import com.rohitrk.shaktigold.model.SubcategoryModel;
import com.rohitrk.shaktigold.service.SubcategoryService;
import com.rohitrk.shaktigold.util.CommonUtil;
import com.rohitrk.shaktigold.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("subcategoryService")
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    SubcategoryDAO subcategoryDAO;

    @Autowired
    CommonUtil commonUtil;

    @Override
    public void addSubcategory(String category, SubcategoryModel subcategory) {
        String fileExtension = StringUtils.substring(subcategory.getImgUrl(),
                StringUtils.indexOf(subcategory.getImgUrl(), "/") + 1,
                StringUtils.indexOf(subcategory.getImgUrl(), ";"));

        String fileName = subcategory.getSubcategoryName().concat(".").concat(fileExtension);

        if (commonUtil.writeImageToDisk(subcategory.getImgUrl(), Constants.SUBCATEGORY_IMG_DIR_PHY.concat(fileName))) {
            subcategory.setImgUrl(Constants.SUBCATEGORY_IMG_DIR + fileName);
            subcategoryDAO.insertSubcategory(category, subcategory);
            subcategoryDAO.insertSubcategoryProperty(subcategory.getSubcategoryName(), subcategory.getProperties());
        } else {
            log.warn("Filed to write image to disk. Category - {} not inserted into disk", subcategory.getSubcategoryName());
        }
    }

    @Override
    public List<SubcategoryModel> getAllSubcategoryUser(String category) {
        return subcategoryDAO.fetchSubcategoryUser(category);
    }

    @Override
    public List<SubCategoryProperty> getItemTemplate(String category, String subcategory) {
        return subcategoryDAO.fetchItemTemplate(category, subcategory);
    }

    @Override
    public List<SubcategoryModel> getAllSubcategoryAdmin(String category) {
        return subcategoryDAO.fetchSubcategoryAdmin(category);
    }

    @Override
    public void deleteSubcategory(String category, String subcategory) {
        subcategoryDAO.deleteSubcategory(category, subcategory);
    }

    @Override
    public void enableDisableSubcategory(String subcategory, boolean enabled) {
        subcategoryDAO.enableDisableSubcategory(subcategory, enabled);
    }
}
