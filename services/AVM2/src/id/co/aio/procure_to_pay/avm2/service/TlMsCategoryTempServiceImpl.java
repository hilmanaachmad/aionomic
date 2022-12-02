/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.avm2.service;

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

import id.co.aio.procure_to_pay.avm2.TlMsCategoryTemp;


/**
 * ServiceImpl object for domain model class TlMsCategoryTemp.
 *
 * @see TlMsCategoryTemp
 */
@Service("AVM2.TlMsCategoryTempService")
@Validated
@EntityService(entityClass = TlMsCategoryTemp.class, serviceId = "AVM2")
public class TlMsCategoryTempServiceImpl implements TlMsCategoryTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsCategoryTempServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsCategoryTempDao")
    private WMGenericDao<TlMsCategoryTemp, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsCategoryTemp, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsCategoryTemp create(TlMsCategoryTemp tlMsCategoryTemp) {
        LOGGER.debug("Creating a new TlMsCategoryTemp with information: {}", tlMsCategoryTemp);

        TlMsCategoryTemp tlMsCategoryTempCreated = this.wmGenericDao.create(tlMsCategoryTemp);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsCategoryTempCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsCategoryTemp getById(Integer tlmscategorytempId) {
        LOGGER.debug("Finding TlMsCategoryTemp by id: {}", tlmscategorytempId);
        return this.wmGenericDao.findById(tlmscategorytempId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsCategoryTemp findById(Integer tlmscategorytempId) {
        LOGGER.debug("Finding TlMsCategoryTemp by id: {}", tlmscategorytempId);
        try {
            return this.wmGenericDao.findById(tlmscategorytempId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsCategoryTemp found with id: {}", tlmscategorytempId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsCategoryTemp> findByMultipleIds(List<Integer> tlmscategorytempIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsCategoryTemps by ids: {}", tlmscategorytempIds);

        return this.wmGenericDao.findByMultipleIds(tlmscategorytempIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsCategoryTemp update(TlMsCategoryTemp tlMsCategoryTemp) {
        LOGGER.debug("Updating TlMsCategoryTemp with information: {}", tlMsCategoryTemp);

        this.wmGenericDao.update(tlMsCategoryTemp);
        this.wmGenericDao.refresh(tlMsCategoryTemp);

        return tlMsCategoryTemp;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsCategoryTemp partialUpdate(Integer tlmscategorytempId, Map<String, Object>tlMsCategoryTempPatch) {
        LOGGER.debug("Partially Updating the TlMsCategoryTemp with id: {}", tlmscategorytempId);

        TlMsCategoryTemp tlMsCategoryTemp = getById(tlmscategorytempId);

        try {
            ObjectReader tlMsCategoryTempReader = this.objectMapper.reader().forType(TlMsCategoryTemp.class).withValueToUpdate(tlMsCategoryTemp);
            tlMsCategoryTemp = tlMsCategoryTempReader.readValue(this.objectMapper.writeValueAsString(tlMsCategoryTempPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsCategoryTempPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsCategoryTemp = update(tlMsCategoryTemp);

        return tlMsCategoryTemp;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsCategoryTemp delete(Integer tlmscategorytempId) {
        LOGGER.debug("Deleting TlMsCategoryTemp with id: {}", tlmscategorytempId);
        TlMsCategoryTemp deleted = this.wmGenericDao.findById(tlmscategorytempId);
        if (deleted == null) {
            LOGGER.debug("No TlMsCategoryTemp found with id: {}", tlmscategorytempId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsCategoryTemp.class.getSimpleName(), tlmscategorytempId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsCategoryTemp tlMsCategoryTemp) {
        LOGGER.debug("Deleting TlMsCategoryTemp with {}", tlMsCategoryTemp);
        this.wmGenericDao.delete(tlMsCategoryTemp);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsCategoryTemp> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsCategoryTemps");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsCategoryTemp> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsCategoryTemps");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsCategoryTemp to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsCategoryTemp to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}