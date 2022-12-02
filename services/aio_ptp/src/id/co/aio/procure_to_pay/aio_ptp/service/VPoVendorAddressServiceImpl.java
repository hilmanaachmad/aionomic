/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.wavemaker.commons.InvalidInputException;
import com.wavemaker.commons.MessageResource;
import com.wavemaker.runtime.data.annotations.EntityService;
import com.wavemaker.runtime.data.dao.WMGenericDao;
import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.VPoVendorAddress;


/**
 * ServiceImpl object for domain model class VPoVendorAddress.
 *
 * @see VPoVendorAddress
 */
@Service("aio_ptp.VPoVendorAddressService")
@Validated
@EntityService(entityClass = VPoVendorAddress.class, serviceId = "aio_ptp")
public class VPoVendorAddressServiceImpl implements VPoVendorAddressService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VPoVendorAddressServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VPoVendorAddressDao")
    private WMGenericDao<VPoVendorAddress, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VPoVendorAddress, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPoVendorAddress create(VPoVendorAddress vpoVendorAddress) {
        LOGGER.debug("Creating a new VPoVendorAddress with information: {}", vpoVendorAddress);

        VPoVendorAddress vpoVendorAddressCreated = this.wmGenericDao.create(vpoVendorAddress);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vpoVendorAddressCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VPoVendorAddress getById(Integer vpovendoraddressId) {
        LOGGER.debug("Finding VPoVendorAddress by id: {}", vpovendoraddressId);
        return this.wmGenericDao.findById(vpovendoraddressId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VPoVendorAddress findById(Integer vpovendoraddressId) {
        LOGGER.debug("Finding VPoVendorAddress by id: {}", vpovendoraddressId);
        try {
            return this.wmGenericDao.findById(vpovendoraddressId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VPoVendorAddress found with id: {}", vpovendoraddressId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VPoVendorAddress> findByMultipleIds(List<Integer> vpovendoraddressIds, boolean orderedReturn) {
        LOGGER.debug("Finding VPoVendorAddresses by ids: {}", vpovendoraddressIds);

        return this.wmGenericDao.findByMultipleIds(vpovendoraddressIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VPoVendorAddress update(VPoVendorAddress vpoVendorAddress) {
        LOGGER.debug("Updating VPoVendorAddress with information: {}", vpoVendorAddress);

        this.wmGenericDao.update(vpoVendorAddress);
        this.wmGenericDao.refresh(vpoVendorAddress);

        return vpoVendorAddress;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPoVendorAddress partialUpdate(Integer vpovendoraddressId, Map<String, Object>vpoVendorAddressPatch) {
        LOGGER.debug("Partially Updating the VPoVendorAddress with id: {}", vpovendoraddressId);

        VPoVendorAddress vpoVendorAddress = getById(vpovendoraddressId);

        try {
            ObjectReader vpoVendorAddressReader = this.objectMapper.reader().forType(VPoVendorAddress.class).withValueToUpdate(vpoVendorAddress);
            vpoVendorAddress = vpoVendorAddressReader.readValue(this.objectMapper.writeValueAsString(vpoVendorAddressPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vpoVendorAddressPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vpoVendorAddress = update(vpoVendorAddress);

        return vpoVendorAddress;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VPoVendorAddress delete(Integer vpovendoraddressId) {
        LOGGER.debug("Deleting VPoVendorAddress with id: {}", vpovendoraddressId);
        VPoVendorAddress deleted = this.wmGenericDao.findById(vpovendoraddressId);
        if (deleted == null) {
            LOGGER.debug("No VPoVendorAddress found with id: {}", vpovendoraddressId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VPoVendorAddress.class.getSimpleName(), vpovendoraddressId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VPoVendorAddress vpoVendorAddress) {
        LOGGER.debug("Deleting VPoVendorAddress with {}", vpoVendorAddress);
        this.wmGenericDao.delete(vpoVendorAddress);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VPoVendorAddress> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VPoVendorAddresses");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VPoVendorAddress> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VPoVendorAddresses");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VPoVendorAddress to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VPoVendorAddress to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}