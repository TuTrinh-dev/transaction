package com.example.grpcclient.service;

import com.example.grpcclient.dto.WalletDTO;
import com.example.grpcclient.entity.Category;
import com.example.grpcclient.entity.GroupCategory;
import com.example.grpcclient.entity.User;
import com.example.grpcclient.entity.Wallet;
import com.example.grpcclient.exception.AppException;
import com.example.grpcclient.repository.CategoryRepository;
import com.example.grpcclient.repository.GroupRepository;
import com.example.grpcclient.repository.UserRepository;
import com.example.grpcclient.repository.WalletRepository;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WalletServiceImpl implements WalletService {
    WalletRepository walletRepository;
    UserRepository userRepository;
    CategoryRepository categoryRepository;
    GroupRepository groupRepository;
    ModelMapper modelMapper;

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "walletList", allEntries = true),}, put = {
            @CachePut(value = "wallet", key = "#walletDTO.id")})
    public WalletDTO createWallet(WalletDTO walletDTO) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new AppException("User not found"));
        Wallet wallet = modelMapper.map(walletDTO, Wallet.class);
        wallet.setUser(user);

        // create default group category
        List<GroupCategory> groupCategoryCategories = groupRepository.findAll();
        wallet.setGroupCategories(Sets.newHashSet(groupCategoryCategories));
//        groupCategoryCategories.stream().forEach(e -> {
//            addDefaultCategory(e.getId());
//        });

        wallet = walletRepository.saveAndFlush(wallet);
        walletDTO = modelMapper.map(wallet, WalletDTO.class);
        walletDTO.setUsername(user.getUsername());
        return walletDTO;
    }

    private void addDefaultCategory(Long groupId) {
        GroupCategory groupCategory = groupRepository.findById(groupId).orElseThrow();
        List<Category> groupCategories = new ArrayList<>();
        switch (groupCategory.getName()) {
            case "Required Expense":
                groupCategories.add(new Category("Food & Beverage", groupCategory));
                groupCategories.add(new Category("Transportation", groupCategory));
                groupCategories.add(new Category("Rentals", groupCategory));
                groupCategories.add(new Category("Water Bill", groupCategory));
                groupCategories.add(new Category("Phone Bill", groupCategory));
                groupCategories.add(new Category("Electricity Bill", groupCategory));
                groupCategories.add(new Category("Gas Bill", groupCategory));
                groupCategories.add(new Category("Television Bill", groupCategory));
                groupCategories.add(new Category("Shopping", groupCategory));
                groupCategories.add(new Category("Internet Bill", groupCategory));
                groupCategories.add(new Category("Other Utility Bill", groupCategory));
                break;
            case "Up & Comers":
                groupCategories.add(new Category("Home Maintenance", groupCategory));
                groupCategories.add(new Category("Vehicle Maintenance", groupCategory));
                groupCategories.add(new Category("Medical Check-up", groupCategory));
                groupCategories.add(new Category("Insurances", groupCategory));
                groupCategories.add(new Category("Education", groupCategory));
                groupCategories.add(new Category("Houseware", groupCategory));
                groupCategories.add(new Category("Personal Items", groupCategory));
                groupCategories.add(new Category("Pets", groupCategory));
                groupCategories.add(new Category("Home Services", groupCategory));
                groupCategories.add(new Category("Other Expense", groupCategory));
                break;
            case "Fun & Relax":
                groupCategories.add(new Category("Fitness", groupCategory));
                groupCategories.add(new Category("Makeup", groupCategory));
                groupCategories.add(new Category("Gifts & Donations", groupCategory));
                groupCategories.add(new Category("Streaming Service", groupCategory));
                groupCategories.add(new Category("Fun Money", groupCategory));
                break;
            case "Investing & Debt Payments":
                groupCategories.add(new Category("Investment", groupCategory));
                groupCategories.add(new Category("Debt Collection", groupCategory));
                groupCategories.add(new Category("Debt", groupCategory));
                groupCategories.add(new Category("Loan", groupCategory));
                groupCategories.add(new Category("Repayment", groupCategory));
                groupCategories.add(new Category("Pay Interest", groupCategory));
                groupCategories.add(new Category("Collect Interest", groupCategory));
                break;
            case "Income":
                groupCategories.add(new Category("Salary", groupCategory));
                groupCategories.add(new Category("Other income", groupCategory));
                break;
        }
        categoryRepository.saveAll(groupCategories);

    }

    @Override
    @Cacheable(value = "walletList")
    public List<WalletDTO> getWalletCurrentUser() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new AppException("User not found"));
        List<Wallet> wallets = walletRepository.findWalletsByUser_Id(user.getId());
        return wallets.stream().map(e -> {
            WalletDTO result = modelMapper.map(e, WalletDTO.class);
            result.setUsername(e.getUser().getUsername());
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public List<WalletDTO> getWalletByUserId() {
        return null;
    }

    @Override
    @Cacheable(value = "wallet", key = "#walletId")
    public Wallet findWalletByWalletId(Long walletId) {
        return walletRepository.findWalletById(walletId).orElseThrow(
                () -> new AppException("Wallet not found"));
    }

    @Override
    public Double getCurrentBalance(Long walletId) {
        return this.findWalletByWalletId(walletId).getCurrentBalance();
    }


    @Override
    @Transactional
    public void updateCurrentBalance(Long walletId, Long groupId, Double amount) {
        Wallet wallet = findWalletByWalletId(walletId);
        double balance = wallet.getCurrentBalance();
        if ((amount < balance || amount == balance) && groupId != 5) {
            balance = balance - amount;
        } else {
            balance = balance + amount;
        }
        wallet.setCurrentBalance(balance);
        walletRepository.save(wallet);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "walletList", allEntries = true),}, put = {
            @CachePut(value = "wallet", key = "#walletDTO.id")})
    public WalletDTO updateWallet(WalletDTO walletDTO) {
        Wallet wallet = findWalletByWalletId(walletDTO.getId());
        wallet.setName(walletDTO.getName());
        wallet.setCurrency(walletDTO.getCurrency());
        wallet.setCurrentBalance(walletDTO.getCurrentBalance());
        wallet = walletRepository.save(wallet);
        return modelMapper.map(wallet, WalletDTO.class);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "walletList", allEntries = true),
            @CacheEvict(value = "wallet", key = "#walletId"),})
    public void deleteWallet(Long walletId) {
        Wallet wallet = findWalletByWalletId(walletId);
        walletRepository.deleteById(wallet.getId());
    }

    @Override
    public Page<WalletDTO> getAllWalletPagination(int page, int size, String sort) {
        String[] sortSplit = sort.split(",");
        return null;
//        return walletRepository.findAll(new PageRequest(page, size, (sortSplit[1].toUpperCase().equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC), sortSplit[0]));
    }

}
