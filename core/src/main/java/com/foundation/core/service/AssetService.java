package com.foundation.core.service;

import com.foundation.core.constants.OpsType;
import com.foundation.core.entity.Asset;
import com.foundation.core.entity.Money;
import com.foundation.core.repository.AssetRepository;
import com.foundation.core.repository.CustomerRepository;
import com.foundation.core.repository.MoneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private MoneyRepository moneyRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public List<Asset> listAssets(Long customerId, Principal principal) {
        // Allow admin to view any customer's orders or restrict to authenticated customer's own orders.
        if (!principal.getName().equals(customerRepository.findByCustomerId(customerId).get().getUsername()) && !isAdmin(principal)) {
            throw new AccessDeniedException("You are not authorized to view these orders");
        }
        return assetRepository.findAllByCustomerId(customerId);
    }

    public int depositMoney(Money money, Principal principal) {
        // Allow admin to view any customer's orders or restrict to authenticated customer's own orders.
        if (!principal.getName().equals(customerRepository.findByCustomerId(money.getCustomerId()).get().getUsername()) && !isAdmin(principal)) {
            throw new AccessDeniedException("You are not authorized to view these orders");
        }
        money.setOpsType(OpsType.DEPOSIT);
        moneyRepository.save(money);
        return assetRepository.depositAmountForCustomer(money.getCustomerId(), money.getAmount());
    }

    public int withdrawMoney(Money money, Principal principal) {
        // Allow admin to view any customer's orders or restrict to authenticated customer's own orders.
        if (!principal.getName().equals(customerRepository.findByCustomerId(money.getCustomerId()).get().getUsername()) && !isAdmin(principal)) {
            throw new AccessDeniedException("You are not authorized to view these orders");
        }
        money.setOpsType(OpsType.WITHDRAW);
        moneyRepository.save(money);
        return assetRepository.withdrawAmountForCustomer(money.getCustomerId(), money.getAmount());
    }

    // Helper method to check if current user is admin.
    private boolean isAdmin(Principal principal){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
