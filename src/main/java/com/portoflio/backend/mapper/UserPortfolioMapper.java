package com.portoflio.backend.mapper;

import com.portoflio.backend.dto.input.InUserPortfolioDTO;
import com.portoflio.backend.dto.output.OutUserPortfolioDTO;
import com.portoflio.backend.model.UserPortfolio;
import org.mapstruct.*;


@Mapper( componentModel = MappingConstants.ComponentModel.SPRING )
public interface UserPortfolioMapper {

    OutUserPortfolioDTO toOutDTO(UserPortfolio userPortfolio);
    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    UserPortfolio toUserPortfolio(InUserPortfolioDTO inUserPortfolioDTO);
}
