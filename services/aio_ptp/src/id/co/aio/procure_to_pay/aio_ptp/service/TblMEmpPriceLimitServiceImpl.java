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

import id.co.aio.procure_to_pay.aio_ptp.TblMEmpPriceLimit;


/**
 * ServiceImpl object for domain model class TblMEmpPriceLimit.
 *
 * @see TblMEmpPriceLimit
 */
@Service("aio_ptp.TblMEmpPriceLimitService")
@Validated
@EntityService(entityClass = TblMEmpPriceLimit.class, serviceId = "aio_ptp")
public class TblMEmpPriceLimitServiceImpl implements TblMEmpPriceLimitService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMEmpPriceLimitServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMEmpPriceLimitDao")
    private WMGenericDao<TblMEmpPriceLimit, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMEmpPriceLimit, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMEmpPriceLimit create(TblMEmpPriceLimit tblMempPriceLimit) {
        LOGGER.debug("Creating a new TblMEmpPriceLimit with information: {}", tblMempPriceLimit);

        TblMEmpPriceLimit tblMempPriceLimitCreated = this.wmGenericDao.create(tblMempPriceLimit);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMempPriceLimitCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMEmpPriceLimit getById(Integer tblmemppricelimitId) {
        LOGGER.debug("Finding TblMEmpPriceLimit by id: {}", tblmemppricelimitId);
        return this.wmGenericDao.findById(tblmemppricelimitId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMEmpPriceLimit findById(Integer tblmemppricelimitId) {
        LOGGER.debug("Finding TblMEmpPriceLimit by id: {}", tblmemppricelimitId);
        try {
            return this.wmGenericDao.findById(tblmemppricelimitId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMEmpPriceLimit found with id: {}", tblmemppricelimitId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMEmpPriceLimit> findByMultipleIds(List<Integer> tblmemppricelimitIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMEmpPriceLimits by ids: {}", tblmemppricelimitIds);

        return this.wmGenericDao.findByMultipleIds(tblmemppricelimitIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMEmpPriceLimit update(TblMEmpPriceLimit tblMempPriceLimit) {
        LOGGER.debug("Updating TblMEmpPriceLimit with information: {}", tblMempPriceLimit);

        this.wmGenericDao.update(tblMempPriceLimit);
        this.wmGenericDao.refresh(tblMempPriceLimit);

        return tblMempPriceLimit;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMEmpPriceLimit partialUpdate(Integer tblmemppricelimitId, Map<String, Object>tblMempPriceLimitPatch) {
        LOGGER.debug("Partially Updating the TblMEmpPriceLimit with id: {}", tblmemppricelimitId);

        TblMEmpPriceLimit tblMempPriceLimit = getById(tblmemppricelimitId);

        try {
            ObjectReader tblMempPriceLimitReader = this.objectMapper.reader().forType(TblMEmpPriceLimit.class).withValueToUpdate(tblMempPriceLimit);
            tblMempPriceLimit = tblMempPriceLimitReader.readValue(this.objectMapper.writeValueAsString(tblMempPriceLimitPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMempPriceLimitPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMempPriceLimit = update(tblMempPriceLimit);

        return tblMempPriceLimit;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMEmpPriceLimit delete(Integer tblmemppricelimitId) {
        LOGGER.debug("Deleting TblMEmpPriceLimit with id: {}", tblmemppricelimitId);
        TblMEmpPriceLimit deleted = this.wmGenericDao.findById(tblmemppricelimitId);
        if (deleted == null) {
            LOGGER.debug("No TblMEmpPriceLimit found with id: {}", tblmemppricelimitId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMEmpPriceLimit.class.getSimpleName(), tblmemppricelimitId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMEmpPriceLimit tblMempPriceLimit) {
        LOGGER.debug("Deleting TblMEmpPriceLimit with {}", tblMempPriceLimit);
        this.wmGenericDao.delete(tblMempPriceLimit);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMEmpPriceLimit> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMEmpPriceLimits");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMEmpPriceLimit> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMEmpPriceLimits");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMEmpPriceLimit to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMEmpPriceLimit to {} format", options.getExportType());
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