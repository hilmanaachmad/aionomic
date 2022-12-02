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

import id.co.aio.procure_to_pay.aio_ptp.TblMMatVendor;


/**
 * ServiceImpl object for domain model class TblMMatVendor.
 *
 * @see TblMMatVendor
 */
@Service("aio_ptp.TblMMatVendorService")
@Validated
@EntityService(entityClass = TblMMatVendor.class, serviceId = "aio_ptp")
public class TblMMatVendorServiceImpl implements TblMMatVendorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMMatVendorServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMMatVendorDao")
    private WMGenericDao<TblMMatVendor, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMMatVendor, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMMatVendor create(TblMMatVendor tblMmatVendor) {
        LOGGER.debug("Creating a new TblMMatVendor with information: {}", tblMmatVendor);

        TblMMatVendor tblMmatVendorCreated = this.wmGenericDao.create(tblMmatVendor);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMmatVendorCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMMatVendor getById(Integer tblmmatvendorId) {
        LOGGER.debug("Finding TblMMatVendor by id: {}", tblmmatvendorId);
        return this.wmGenericDao.findById(tblmmatvendorId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMMatVendor findById(Integer tblmmatvendorId) {
        LOGGER.debug("Finding TblMMatVendor by id: {}", tblmmatvendorId);
        try {
            return this.wmGenericDao.findById(tblmmatvendorId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMMatVendor found with id: {}", tblmmatvendorId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMMatVendor> findByMultipleIds(List<Integer> tblmmatvendorIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMMatVendors by ids: {}", tblmmatvendorIds);

        return this.wmGenericDao.findByMultipleIds(tblmmatvendorIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMMatVendor update(TblMMatVendor tblMmatVendor) {
        LOGGER.debug("Updating TblMMatVendor with information: {}", tblMmatVendor);

        this.wmGenericDao.update(tblMmatVendor);
        this.wmGenericDao.refresh(tblMmatVendor);

        return tblMmatVendor;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMMatVendor partialUpdate(Integer tblmmatvendorId, Map<String, Object>tblMmatVendorPatch) {
        LOGGER.debug("Partially Updating the TblMMatVendor with id: {}", tblmmatvendorId);

        TblMMatVendor tblMmatVendor = getById(tblmmatvendorId);

        try {
            ObjectReader tblMmatVendorReader = this.objectMapper.reader().forType(TblMMatVendor.class).withValueToUpdate(tblMmatVendor);
            tblMmatVendor = tblMmatVendorReader.readValue(this.objectMapper.writeValueAsString(tblMmatVendorPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMmatVendorPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMmatVendor = update(tblMmatVendor);

        return tblMmatVendor;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMMatVendor delete(Integer tblmmatvendorId) {
        LOGGER.debug("Deleting TblMMatVendor with id: {}", tblmmatvendorId);
        TblMMatVendor deleted = this.wmGenericDao.findById(tblmmatvendorId);
        if (deleted == null) {
            LOGGER.debug("No TblMMatVendor found with id: {}", tblmmatvendorId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMMatVendor.class.getSimpleName(), tblmmatvendorId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMMatVendor tblMmatVendor) {
        LOGGER.debug("Deleting TblMMatVendor with {}", tblMmatVendor);
        this.wmGenericDao.delete(tblMmatVendor);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMMatVendor> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMMatVendors");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMMatVendor> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMMatVendors");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMMatVendor to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMMatVendor to {} format", options.getExportType());
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