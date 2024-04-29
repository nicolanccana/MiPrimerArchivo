package org.e2e.labe2e02.driver.infrastructure;


import org.e2e.labe2e02.driver.domain.Category;
import org.e2e.labe2e02.driver.domain.Driver;
import org.e2e.labe2e02.user.infrastructure.BaseUserRepository;

import java.util.List;

public interface DriverRepository extends BaseUserRepository<Driver> {
    List<Driver> findAllByCategory(Category category);

}