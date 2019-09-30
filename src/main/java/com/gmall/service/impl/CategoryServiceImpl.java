package com.gmall.service.impl;

import com.gmall.common.ServerResponse;
import com.gmall.dao.CategoryMapper;
import com.gmall.pojo.Category;
import com.gmall.service.ICategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service("iCategoryService")
public class CategoryServiceImpl implements ICategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public ServerResponse<String> addCategory(String categoryName, Integer parentId){
        if (parentId == null || StringUtils.isBlank(categoryName)) return ServerResponse.createByErrorMessage("添加分类参数错误");
        // 检查下是否有重名的分类名称
        int resultCount = categoryMapper.checkRepeatCategoryName(categoryName);
        if (resultCount == 0){
            Category category = new Category();
            category.setName(categoryName);
            category.setParentId(parentId);
            category.setStatus(true);
            int rowCount = categoryMapper.insertSelective(category);
            if (rowCount > 0){
                return ServerResponse.createBySuccessMessage("添加分类成功");
            }
        }
        if (resultCount == 1){
            return ServerResponse.createByErrorMessage("分类已存在，不能重复添加");
        }
        return ServerResponse.createByErrorMessage("添加分类失败");
    }

    @Override
    public ServerResponse<String> setCategoryName(String categoryName, Integer categoryId) {
        if (categoryId == null || categoryId < 0 || StringUtils.isBlank(categoryName)) return ServerResponse.createByErrorMessage("修改分类名称入参错误");
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) return ServerResponse.createBySuccessMessage("更新分类名称成功");
        return ServerResponse.createByErrorMessage("更新分类名称失败");
    }

    @Override
    public ServerResponse<List<Category>> getChildParallelCategory(Integer parentId) {
        List<Category> categoryList = categoryMapper.selectChildCategoryByParentId(parentId);
        return ServerResponse.createBySuccess(categoryList);
    }

    @Override
    public ServerResponse<List<Integer>> getSelfAndChildCategoryId(Integer categoryId) {
        Set<Category> categorySet = new HashSet<>();
        List<Integer> categoryIdList = new ArrayList<>();
        if (categoryId != null){
            categorySet = findChildCategory(categorySet, categoryId);
            for (Category categoryItem : categorySet){
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);
    }

    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId){
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null){
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectChildCategoryByParentId(categoryId);
        if (!CollectionUtils.isEmpty(categoryList)){
            for (Category categoryItem : categoryList){
                findChildCategory(categorySet, categoryItem.getId());
            }
        }
        return categorySet;
    }
}
