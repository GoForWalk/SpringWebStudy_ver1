package com.example.study.service;

import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryApiLogicService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category>{

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();

        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();

        return Header.Ok(response(baseRepository.save(category)));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRepository.findById(id).map(newCategory -> Header.Ok(response(newCategory))).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();

        return baseRepository.findById(body.getId()).map(category -> {

            category.setTitle(body.getTitle()).setType(body.getType());
            return category;

        }).map(updateCategory -> baseRepository.save(updateCategory))
                .map(newCategory -> Header.Ok(response(newCategory)))
                .orElseGet(() ->Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id).map(deleteCategory ->{baseRepository.delete(deleteCategory); return Header.Ok();}).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<List<CategoryApiResponse>> search(Pageable pageable) {

        Page<Category> categories = baseRepository.findAll(pageable);

        List<CategoryApiResponse> categoryApiResponseList = categories.stream()
                .map(category -> response(category))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalElements(categories.getTotalElements())
                .totalPage(categories.getTotalPages())
                .currentPage(categories.getNumber())
                .currentElements(categories.getNumberOfElements())
                .build();

        return Header.Ok(categoryApiResponseList, pagination);
    }

    public CategoryApiResponse response (Category category){

        CategoryApiResponse categoryApiResponse = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();

        return categoryApiResponse;
    }
}
