package com.example.study.service;

import com.example.study.model.entity.Category;
import com.example.study.model.network.Header;
import com.example.study.model.network.request.CategoryApiRequest;
import com.example.study.model.network.response.CategoryApiResponse;
import org.springframework.stereotype.Service;

@Service
public class CategoryApiLogicService extends BaseService<CategoryApiRequest, CategoryApiResponse, Category>{

    @Override
    public Header<CategoryApiResponse> create(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();

        Category category = Category.builder()
                .type(body.getType())
                .title(body.getTitle())
                .build();

        return response(baseRepository.save(category));
    }

    @Override
    public Header<CategoryApiResponse> read(Long id) {
        return baseRepository.findById(id).map(this::response).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<CategoryApiResponse> update(Header<CategoryApiRequest> request) {

        CategoryApiRequest body = request.getData();

        return baseRepository.findById(body.getId()).map(category -> {

            category.setTitle(body.getTitle()).setType(body.getType());
            return category;

        }).map(updateCategory -> baseRepository.save(updateCategory))
                .map(this::response)
                .orElseGet(() ->Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {
        return baseRepository.findById(id).map(deleteCategory ->{baseRepository.delete(deleteCategory); return Header.Ok();}).orElseGet(()->Header.ERROR("NO DATA"));
    }

    private Header<CategoryApiResponse> response (Category category){

        CategoryApiResponse categoryApiResponse = CategoryApiResponse.builder()
                .id(category.getId())
                .title(category.getTitle())
                .type(category.getType())
                .build();

        return Header.Ok(categoryApiResponse);
    }
}
