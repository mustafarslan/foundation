package com.foundation.core.repository;

import com.foundation.core.entity.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AssetRepository extends JpaRepository<Asset, Long> {
    Optional<Asset> findByCustomerIdAndAssetName(Long customerId, String assetName);
    List<Asset> findAllByCustomerId(Long customerId);

    @Modifying
    @Transactional
    @Query("UPDATE Asset asset SET asset.size = asset.size + :increment, asset.usableSize = asset.usableSize + :increment WHERE asset.customerId = :customerId and asset.assetName = 'TRY'")
    int depositAmountForCustomer(@Param("customerId")Long customerId, @Param("increment") int increment);


    @Modifying
    @Transactional
    @Query("UPDATE Asset asset SET asset.size = asset.size - :decrement, asset.usableSize = asset.usableSize - :decrement WHERE asset.customerId = :customerId and asset.assetName = 'TRY'")
    int withdrawAmountForCustomer(@Param("customerId")Long customerId, @Param("decrement") int decrement);

    @Modifying
    @Transactional
    @Query("UPDATE Asset asset SET asset.usableSize = asset.usableSize - :decrement WHERE asset.customerId = :customerId and asset.assetName = 'TRY'")
    void createOrderToUpdateAsset(@Param("customerId")Long customerId, @Param("decrement") int decrement);

    @Modifying
    @Transactional
    @Query("UPDATE Asset asset SET asset.usableSize = asset.usableSize + :increment WHERE asset.customerId = :customerId and asset.assetName = 'TRY'")
    void cancelOrderToUpdateAsset(@Param("customerId")Long customerId, @Param("increment") int increment);



}
