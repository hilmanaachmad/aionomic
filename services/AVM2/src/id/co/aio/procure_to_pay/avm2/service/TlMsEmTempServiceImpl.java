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

import id.co.aio.procure_to_pay.avm2.TlMsEmTemp;


/**
 * ServiceImpl object for domain model class TlMsEmTemp.
 *
 * @see TlMsEmTemp
 */
@Service("AVM2.TlMsEmTempService")
@Validated
@EntityService(entityClass = TlMsEmTemp.class, serviceId = "AVM2")
public class TlMsEmTempServiceImpl implements TlMsEmTempService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsEmTempServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsEmTempDao")
    private WMGenericDao<TlMsEmTemp, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsEmTemp, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsEmTemp create(TlMsEmTemp tlMsEmTemp) {
        LOGGER.debug("Creating a new TlMsEmTemp with information: {}", tlMsEmTemp);

        TlMsEmTemp tlMsEmTempCreated = this.wmGenericDao.create(tlMsEmTemp);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsEmTempCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsEmTemp getById(Integer tlmsemtempId) {
        LOGGER.debug("Finding TlMsEmTemp by id: {}", tlmsemtempId);
        return this.wmGenericDao.findById(tlmsemtempId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsEmTemp findById(Integer tlmsemtempId) {
        LOGGER.debug("Finding TlMsEmTemp by id: {}", tlmsemtempId);
        try {
            return this.wmGenericDao.findById(tlmsemtempId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsEmTemp found with id: {}", tlmsemtempId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsEmTemp> findByMultipleIds(List<Integer> tlmsemtempIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsEmTemps by ids: {}", tlmsemtempIds);

        return this.wmGenericDao.findByMultipleIds(tlmsemtempIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsEmTemp update(TlMsEmTemp tlMsEmTemp) {
        LOGGER.debug("Updating TlMsEmTemp with information: {}", tlMsEmTemp);

        this.wmGenericDao.update(tlMsEmTemp);
        this.wmGenericDao.refresh(tlMsEmTemp);

        return tlMsEmTemp;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsEmTemp partialUpdate(Integer tlmsemtempId, Map<String, Object>tlMsEmTempPatch) {
        LOGGER.debug("Partially Updating the TlMsEmTemp with id: {}", tlmsemtempId);

        TlMsEmTemp tlMsEmTemp = getById(tlmsemtempId);

        try {
            ObjectReader tlMsEmTempReader = this.objectMapper.reader().forType(TlMsEmTemp.class).withValueToUpdate(tlMsEmTemp);
            tlMsEmTemp = tlMsEmTempReader.readValue(this.objectMapper.writeValueAsString(tlMsEmTempPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsEmTempPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsEmTemp = update(tlMsEmTemp);

        return tlMsEmTemp;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsEmTemp delete(Integer tlmsemtempId) {
        LOGGER.debug("Deleting TlMsEmTemp with id: {}", tlmsemtempId);
        TlMsEmTemp deleted = this.wmGenericDao.findById(tlmsemtempId);
        if (deleted == null) {
            LOGGER.debug("No TlMsEmTemp found with id: {}", tlmsemtempId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsEmTemp.class.getSimpleName(), tlmsemtempId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsEmTemp tlMsEmTemp) {
        LOGGER.debug("Deleting TlMsEmTemp with {}", tlMsEmTemp);
        this.wmGenericDao.delete(tlMsEmTemp);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsEmTemp> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsEmTemps");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsEmTemp> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsEmTemps");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsEmTemp to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsEmTemp to {} format", options.getExportType());
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