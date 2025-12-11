package com.specialist.profile.models;

import com.specialist.contracts.profile.ProfileType;
import com.specialist.utils.pagination.PageDataHolder;
import com.specialist.utils.pagination.PageRequest;
import lombok.Getter;

@Getter
public class ProfileFilter extends PageRequest implements PageDataHolder {

        public ProfileFilter(Integer pageNumber, Integer pageSize, Boolean asc, ProfileType type) {
                super(pageNumber, pageSize, asc);
        }
}
