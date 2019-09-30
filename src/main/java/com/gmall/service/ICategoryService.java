package com.gmall.service;

import com.gmall.common.ServerResponse;
import com.gmall.pojo.Category;

import java.util.List;


public interface ICategoryService {

    ServerResponse<String> addCategory(String categoryName, Integer parentId);

    ServerResponse<String> setCategoryName(String categoryName, Integer categoryId);

    ServerResponse<List<Category>> getChildParallelCategory(Integer parentId);

    ServerResponse<List<Integer>> getSelfAndChildCategoryId(Integer categoryId);
}
