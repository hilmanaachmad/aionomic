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

import id.co.aio.procure_to_pay.avm2.TlMsPuonEntity;


/**
 * ServiceImpl object for domain model class TlMsPuonEntity.
 *
 * @see TlMsPuonEntity
 */
@Service("AVM2.TlMsPuonEntityService")
@Validated
@EntityService(entityClass = TlMsPuonEntity.class, serviceId = "AVM2")
public class TlMsPuonEntityServiceImpl implements TlMsPuonEntityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsPuonEntityServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsPuonEntityDao")
    private WMGenericDao<TlMsPuonEntity, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsPuonEntity, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsPuonEntity create(TlMsPuonEntity tlMsPuonEntity) {
        LOGGER.debug("Creating a new TlMsPuonEntity with information: {}", tlMsPuonEntity);

        TlMsPuonEntity tlMsPuonEntityCreated = this.wmGenericDao.create(tlMsPuonEntity);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsPuonEntityCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsPuonEntity getById(Integer tlmspuonentityId) {
        LOGGER.debug("Finding TlMsPuonEntity by id: {}", tlmspuonentityId);
        return this.wmGenericDao.findById(tlmspuonentityId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsPuonEntity findById(Integer tlmspuonentityId) {
        LOGGER.debug("Finding TlMsPuonEntity by id: {}", tlmspuonentityId);
        try {
            return this.wmGenericDao.findById(tlmspuonentityId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsPuonEntity found with id: {}", tlmspuonentityId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsPuonEntity> findByMultipleIds(List<Integer> tlmspuonentityIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsPuonEntities by ids: {}", tlmspuonentityIds);

        return this.wmGenericDao.findByMultipleIds(tlmspuonentityIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsPuonEntity update(TlMsPuonEntity tlMsPuonEntity) {
        LOGGER.debug("Updating TlMsPuonEntity with information: {}", tlMsPuonEntity);

        this.wmGenericDao.update(tlMsPuonEntity);
        this.wmGenericDao.refresh(tlMsPuonEntity);

        return tlMsPuonEntity;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsPuonEntity partialUpdate(Integer tlmspuonentityId, Map<String, Object>tlMsPuonEntityPatch) {
        LOGGER.debug("Partially Updating the TlMsPuonEntity with id: {}", tlmspuonentityId);

        TlMsPuonEntity tlMsPuonEntity = getById(tlmspuonentityId);

        try {
            ObjectReader tlMsPuonEntityReader = this.objectMapper.reader().forType(TlMsPuonEntity.class).withValueToUpdate(tlMsPuonEntity);
            tlMsPuonEntity = tlMsPuonEntityReader.readValue(this.objectMapper.writeValueAsString(tlMsPuonEntityPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsPuonEntityPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsPuonEntity = update(tlMsPuonEntity);

        return tlMsPuonEntity;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsPuonEntity delete(Integer tlmspuonentityId) {
        LOGGER.debug("Deleting TlMsPuonEntity with id: {}", tlmspuonentityId);
        TlMsPuonEntity deleted = this.wmGenericDao.findById(tlmspuonentityId);
        if (deleted == null) {
            LOGGER.debug("No TlMsPuonEntity found with id: {}", tlmspuonentityId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsPuonEntity.class.getSimpleName(), tlmspuonentityId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsPuonEntity tlMsPuonEntity) {
        LOGGER.debug("Deleting TlMsPuonEntity with {}", tlMsPuonEntity);
        this.wmGenericDao.delete(tlMsPuonEntity);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsPuonEntity> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsPuonEntities");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsPuonEntity> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsPuonEntities");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsPuonEntity to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsPuonEntity to {} format", options.getExportType());
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