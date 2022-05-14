package by.ukrop.diploma.service;

import by.ukrop.diploma.persistence.dao.DiscountDAO;
import by.ukrop.diploma.persistence.entity.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    private DiscountDAO discountDAO;

    @Override
    @Transactional
    public Discount getDiscount(Long id) {
        return discountDAO.getDiscount(id);
    }
}
