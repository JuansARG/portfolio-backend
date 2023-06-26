package com.portoflio.backend.mapper;

import com.portoflio.backend.dto.request.UserPortfolioRequest;
import com.portoflio.backend.dto.response.UserPortfolioResponse;
import com.portoflio.backend.model.UserPortfolio;
import org.mapstruct.*;


@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface UserPortfolioMapper {

    UserPortfolioResponse toOutDTO(UserPortfolio userPortfolio);
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "roles", ignore = true)
    })
    UserPortfolio toUserPortfolio(UserPortfolioRequest userPortfolioRequest);
}
