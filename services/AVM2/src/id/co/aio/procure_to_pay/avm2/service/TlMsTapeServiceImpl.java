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

import id.co.aio.procure_to_pay.avm2.TlMsTape;


/**
 * ServiceImpl object for domain model class TlMsTape.
 *
 * @see TlMsTape
 */
@Service("AVM2.TlMsTapeService")
@Validated
@EntityService(entityClass = TlMsTape.class, serviceId = "AVM2")
public class TlMsTapeServiceImpl implements TlMsTapeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsTapeServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsTapeDao")
    private WMGenericDao<TlMsTape, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsTape, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsTape create(TlMsTape tlMsTape) {
        LOGGER.debug("Creating a new TlMsTape with information: {}", tlMsTape);

        TlMsTape tlMsTapeCreated = this.wmGenericDao.create(tlMsTape);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsTapeCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsTape getById(Integer tlmstapeId) {
        LOGGER.debug("Finding TlMsTape by id: {}", tlmstapeId);
        return this.wmGenericDao.findById(tlmstapeId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsTape findById(Integer tlmstapeId) {
        LOGGER.debug("Finding TlMsTape by id: {}", tlmstapeId);
        try {
            return this.wmGenericDao.findById(tlmstapeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsTape found with id: {}", tlmstapeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsTape> findByMultipleIds(List<Integer> tlmstapeIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsTapes by ids: {}", tlmstapeIds);

        return this.wmGenericDao.findByMultipleIds(tlmstapeIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsTape update(TlMsTape tlMsTape) {
        LOGGER.debug("Updating TlMsTape with information: {}", tlMsTape);

        this.wmGenericDao.update(tlMsTape);
        this.wmGenericDao.refresh(tlMsTape);

        return tlMsTape;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsTape partialUpdate(Integer tlmstapeId, Map<String, Object>tlMsTapePatch) {
        LOGGER.debug("Partially Updating the TlMsTape with id: {}", tlmstapeId);

        TlMsTape tlMsTape = getById(tlmstapeId);

        try {
            ObjectReader tlMsTapeReader = this.objectMapper.reader().forType(TlMsTape.class).withValueToUpdate(tlMsTape);
            tlMsTape = tlMsTapeReader.readValue(this.objectMapper.writeValueAsString(tlMsTapePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsTapePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsTape = update(tlMsTape);

        return tlMsTape;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsTape delete(Integer tlmstapeId) {
        LOGGER.debug("Deleting TlMsTape with id: {}", tlmstapeId);
        TlMsTape deleted = this.wmGenericDao.findById(tlmstapeId);
        if (deleted == null) {
            LOGGER.debug("No TlMsTape found with id: {}", tlmstapeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsTape.class.getSimpleName(), tlmstapeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsTape tlMsTape) {
        LOGGER.debug("Deleting TlMsTape with {}", tlMsTape);
        this.wmGenericDao.delete(tlMsTape);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsTape> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsTapes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsTape> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsTapes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsTape to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsTape to {} format", options.getExportType());
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