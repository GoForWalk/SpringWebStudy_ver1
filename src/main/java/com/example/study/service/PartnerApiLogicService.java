package com.example.study.service;

import com.example.study.model.entity.Partner;
import com.example.study.model.enumclass.PartnerStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.PartnerApiRequest;
import com.example.study.model.network.response.PartnerApiResponse;
import com.example.study.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartnerApiLogicService extends BaseService<PartnerApiRequest, PartnerApiResponse, Partner> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Header<PartnerApiResponse> create(Header<PartnerApiRequest> request) {

        PartnerApiRequest body = request.getData();

        Partner partner = Partner.builder()
                .name(body.getName())
                .status("REGISTERED")
                .address(body.getAddress())
                .callCenter(body.getCallCenter())
                .partnerNumber(body.getPartnerNumber())
                .businessNumber(body.getBusinessNumber())
                .ceoName(body.getCeoName())
                .registeredAt(body.getRegisteredAt())
                .unregisteredAt(body.getUnregisteredAt())
                .category(categoryRepository.getOne(body.getCategoryId()))
                .build();

        return Header.Ok(response(baseRepository.save(partner)));
    }

    @Override
    public Header<PartnerApiResponse> read(Long id) {

        return baseRepository.findById(id).map(partner -> Header.Ok(response(partner))).orElseGet(()->Header.ERROR("NO DATA"));
    }

    @Override
    public Header<PartnerApiResponse> update(Header<PartnerApiRequest> request) {

        PartnerApiRequest body = request.getData();

        return baseRepository.findById(body.getId()).map(partner -> {

            partner.setName(body.getName())
                    .setStatus(body.getStatus())
                    .setAddress(body.getAddress())
                    .setCallCenter(body.getCallCenter())
                    .setPartnerNumber(body.getPartnerNumber())
                    .setBusinessNumber(body.getBusinessNumber())
                    .setCeoName(body.getCeoName())
                    .setRegisteredAt(body.getRegisteredAt())
                    .setUnregisteredAt(body.getUnregisteredAt())
                    .setCategory(categoryRepository.getOne(body.getCategoryId()))
                    ;
            return partner;
        }).map(updatPartner ->baseRepository.save(updatPartner))
                .map(newPartner -> Header.Ok(response((newPartner))))
                .orElseGet(() -> Header.ERROR("NO DATA"));
    }

    @Override
    public Header delete(Long id) {

        return baseRepository.findById(id)
                .map(deletePartner -> {baseRepository.delete(deletePartner); return Header.Ok();})
                .orElseGet(() ->Header.ERROR("NO DATA"));
    }

    @Override // pagination
    public Header<List<PartnerApiResponse>> search(Pageable pageable) {

        Page<Partner> partners = baseRepository.findAll(pageable);

        // List<PartnerApiResponse>
        List<PartnerApiResponse> partnerApiResponseList = partners.stream()
                .map(partner -> response(partner))
                .collect(Collectors.toList());

        // Header - pagination 에 페이징 정보 추가
        Pagination pagination = Pagination.builder()
                .totalPage(partners.getTotalPages())
                .totalElements(partners.getTotalElements())
                .currentElements(partners.getNumberOfElements())
                .currentPage(partners.getNumber())
                .build();

        return Header.Ok(partnerApiResponseList, pagination); // TODO
    }

    public PartnerApiResponse response(Partner partner){

        PartnerApiResponse partnerApiResponse = PartnerApiResponse.builder()
                .id(partner.getId())
                .name(partner.getName())
                .status(partner.getStatus())
                .address(partner.getAddress())
                .callCenter(partner.getCallCenter())
                .partnerNumber(partner.getPartnerNumber())
                .ceoName(partner.getCeoName())
                .registeredAt(partner.getRegisteredAt())
                .unregisteredAt(partner.getUnregisteredAt())
                .categoryId(partner.getCategory().getId())
                .build();

        return partnerApiResponse;
    }
}
