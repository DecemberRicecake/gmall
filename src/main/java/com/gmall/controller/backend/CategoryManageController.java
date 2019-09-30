package com.gmall.controller.backend;


import com.gmall.common.Const;
import com.gmall.common.ServerResponse;
import com.gmall.pojo.Category;
import com.gmall.pojo.User;
import com.gmall.service.ICategoryService;
import com.gmall.service.IUserService;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private ICategoryService iCategoryService;

    @RequestMapping(value = "addCategory", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> addCategory(HttpSession session, String categoryName, @RequestParam(defaultValue = "0") Integer parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("账号未登录，没有权限访问");
        } else {
            return iCategoryService.addCategory(categoryName, parentId);
        }
    }

    @RequestMapping(value = "setCategoryName", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> setCategoryName(HttpSession session, String categoryName, Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("账号未登录，没有权限访问");
        } else {
            return iCategoryService.setCategoryName(categoryName, categoryId);
        }
    }

    @RequestMapping(value = "getChildParallelCategory", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Category>> getChildParallelCategory(HttpSession session, @RequestParam(defaultValue = "0") Integer parentId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("账号未登录，没有权限访问");
        } else {
            return iCategoryService.getChildParallelCategory(parentId);
        }
    }

    @RequestMapping(value = "getSelfAndChildCategoryId", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Integer>>  getSelfAndChildCategory(HttpSession session, @RequestParam(defaultValue = "0") Integer categoryId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorMessage("账号未登录，没有权限访问");
        } else {
            return iCategoryService.getSelfAndChildCategoryId(categoryId);
        }
    }

}