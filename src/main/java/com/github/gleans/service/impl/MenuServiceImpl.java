package com.github.gleans.service.impl;

import com.github.gleans.model.Menu;
import com.github.gleans.repository.MenuRepository;
import com.github.gleans.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private MenuRepository menuRepository;

    @Autowired
    public void setMenuRepository(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Menu> getAllRolesByMenus() {
        return menuRepository.findAll();
    }
}
