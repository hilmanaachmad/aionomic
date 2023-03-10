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

import id.co.aio.procure_to_pay.avm2.TlMsEm;


/**
 * ServiceImpl object for domain model class TlMsEm.
 *
 * @see TlMsEm
 */
@Service("AVM2.TlMsEmService")
@Validated
@EntityService(entityClass = TlMsEm.class, serviceId = "AVM2")
public class TlMsEmServiceImpl implements TlMsEmService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsEmServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsEmDao")
    private WMGenericDao<TlMsEm, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsEm, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsEm create(TlMsEm tlMsEm) {
        LOGGER.debug("Creating a new TlMsEm with information: {}", tlMsEm);

        TlMsEm tlMsEmCreated = this.wmGenericDao.create(tlMsEm);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsEmCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsEm getById(Integer tlmsemId) {
        LOGGER.debug("Finding TlMsEm by id: {}", tlmsemId);
        return this.wmGenericDao.findById(tlmsemId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsEm findById(Integer tlmsemId) {
        LOGGER.debug("Finding TlMsEm by id: {}", tlmsemId);
        try {
            return this.wmGenericDao.findById(tlmsemId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsEm found with id: {}", tlmsemId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsEm> findByMultipleIds(List<Integer> tlmsemIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsEms by ids: {}", tlmsemIds);

        return this.wmGenericDao.findByMultipleIds(tlmsemIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsEm update(TlMsEm tlMsEm) {
        LOGGER.debug("Updating TlMsEm with information: {}", tlMsEm);

        this.wmGenericDao.update(tlMsEm);
        this.wmGenericDao.refresh(tlMsEm);

        return tlMsEm;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsEm partialUpdate(Integer tlmsemId, Map<String, Object>tlMsEmPatch) {
        LOGGER.debug("Partially Updating the TlMsEm with id: {}", tlmsemId);

        TlMsEm tlMsEm = getById(tlmsemId);

        try {
            ObjectReader tlMsEmReader = this.objectMapper.reader().forType(TlMsEm.class).withValueToUpdate(tlMsEm);
            tlMsEm = tlMsEmReader.readValue(this.objectMapper.writeValueAsString(tlMsEmPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsEmPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsEm = update(tlMsEm);

        return tlMsEm;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsEm delete(Integer tlmsemId) {
        LOGGER.debug("Deleting TlMsEm with id: {}", tlmsemId);
        TlMsEm deleted = this.wmGenericDao.findById(tlmsemId);
        if (deleted == null) {
            LOGGER.debug("No TlMsEm found with id: {}", tlmsemId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsEm.class.getSimpleName(), tlmsemId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsEm tlMsEm) {
        LOGGER.debug("Deleting TlMsEm with {}", tlMsEm);
        this.wmGenericDao.delete(tlMsEm);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsEm> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsEms");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsEm> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsEms");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsEm to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsEm to {} format", options.getExportType());
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