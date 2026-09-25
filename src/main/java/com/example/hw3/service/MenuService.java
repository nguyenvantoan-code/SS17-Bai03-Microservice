package com.example.hw3.service;

import com.example.hw3.model.Menu;
import com.example.hw3.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;

    @Cacheable(value = "menuCache", key = "#id")
    public Menu getMenu(Long id) {
        log.info("Lấy từ database: Menu id = {}", id);
        return menuRepository.findById(id);
    }

    @CacheEvict(value = "menuCache", key = "#menu.id")
    public void updateMenu(Menu menu) {
        log.info("Cập nhật Database cho Menu id = {}", menu.getId());
        menuRepository.save(menu);
        log.info("Đã xóa cache cho Menu id = {}", menu.getId());
    }
}
