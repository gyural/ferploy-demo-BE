package com.FerployDemo.ferployDemo.domain.response;

import com.FerployDemo.ferployDemo.domain.entity.NameCard;
import com.FerployDemo.ferployDemo.dto.NameCardDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetNameCardListResponse {
    private List<NameCardDTO> myNameCardList;
    private List<NameCardDTO> NameCardList;
}
