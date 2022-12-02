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

import id.co.aio.procure_to_pay.avm2.TlMsBaEntity;


/**
 * ServiceImpl object for domain model class TlMsBaEntity.
 *
 * @see TlMsBaEntity
 */
@Service("AVM2.TlMsBaEntityService")
@Validated
@EntityService(entityClass = TlMsBaEntity.class, serviceId = "AVM2")
public class TlMsBaEntityServiceImpl implements TlMsBaEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsBaEntityServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsBaEntityDao")
    private WMGenericDao<TlMsBaEntity, String> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsBaEntity, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsBaEntity create(TlMsBaEntity tlMsBaEntity) {
        LOGGER.debug("Creating a new TlMsBaEntity with information: {}", tlMsBaEntity);

        TlMsBaEntity tlMsBaEntityCreated = this.wmGenericDao.create(tlMsBaEntity);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsBaEntityCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsBaEntity getById(String tlmsbaentityId) {
        LOGGER.debug("Finding TlMsBaEntity by id: {}", tlmsbaentityId);
        return this.wmGenericDao.findById(tlmsbaentityId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsBaEntity findById(String tlmsbaentityId) {
        LOGGER.debug("Finding TlMsBaEntity by id: {}", tlmsbaentityId);
        try {
            return this.wmGenericDao.findById(tlmsbaentityId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsBaEntity found with id: {}", tlmsbaentityId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsBaEntity> findByMultipleIds(List<String> tlmsbaentityIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsBaEntities by ids: {}", tlmsbaentityIds);

        return this.wmGenericDao.findByMultipleIds(tlmsbaentityIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsBaEntity update(TlMsBaEntity tlMsBaEntity) {
        LOGGER.debug("Updating TlMsBaEntity with information: {}", tlMsBaEntity);

        this.wmGenericDao.update(tlMsBaEntity);
        this.wmGenericDao.refresh(tlMsBaEntity);

        return tlMsBaEntity;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsBaEntity partialUpdate(String tlmsbaentityId, Map<String, Object>tlMsBaEntityPatch) {
        LOGGER.debug("Partially Updating the TlMsBaEntity with id: {}", tlmsbaentityId);

        TlMsBaEntity tlMsBaEntity = getById(tlmsbaentityId);

        try {
            ObjectReader tlMsBaEntityReader = this.objectMapper.reader().forType(TlMsBaEntity.class).withValueToUpdate(tlMsBaEntity);
            tlMsBaEntity = tlMsBaEntityReader.readValue(this.objectMapper.writeValueAsString(tlMsBaEntityPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsBaEntityPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsBaEntity = update(tlMsBaEntity);

        return tlMsBaEntity;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsBaEntity delete(String tlmsbaentityId) {
        LOGGER.debug("Deleting TlMsBaEntity with id: {}", tlmsbaentityId);
        TlMsBaEntity deleted = this.wmGenericDao.findById(tlmsbaentityId);
        if (deleted == null) {
            LOGGER.debug("No TlMsBaEntity found with id: {}", tlmsbaentityId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsBaEntity.class.getSimpleName(), tlmsbaentityId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsBaEntity tlMsBaEntity) {
        LOGGER.debug("Deleting TlMsBaEntity with {}", tlMsBaEntity);
        this.wmGenericDao.delete(tlMsBaEntity);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsBaEntity> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsBaEntities");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsBaEntity> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsBaEntities");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsBaEntity to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsBaEntity to {} format", options.getExportType());
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