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

import id.co.aio.procure_to_pay.avm2.TlMsLoc;


/**
 * ServiceImpl object for domain model class TlMsLoc.
 *
 * @see TlMsLoc
 */
@Service("AVM2.TlMsLocService")
@Validated
@EntityService(entityClass = TlMsLoc.class, serviceId = "AVM2")
public class TlMsLocServiceImpl implements TlMsLocService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsLocServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsLocDao")
    private WMGenericDao<TlMsLoc, Long> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsLoc, Long> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsLoc create(TlMsLoc tlMsLoc) {
        LOGGER.debug("Creating a new TlMsLoc with information: {}", tlMsLoc);

        TlMsLoc tlMsLocCreated = this.wmGenericDao.create(tlMsLoc);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsLocCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsLoc getById(Long tlmslocId) {
        LOGGER.debug("Finding TlMsLoc by id: {}", tlmslocId);
        return this.wmGenericDao.findById(tlmslocId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsLoc findById(Long tlmslocId) {
        LOGGER.debug("Finding TlMsLoc by id: {}", tlmslocId);
        try {
            return this.wmGenericDao.findById(tlmslocId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsLoc found with id: {}", tlmslocId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsLoc> findByMultipleIds(List<Long> tlmslocIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsLocs by ids: {}", tlmslocIds);

        return this.wmGenericDao.findByMultipleIds(tlmslocIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsLoc update(TlMsLoc tlMsLoc) {
        LOGGER.debug("Updating TlMsLoc with information: {}", tlMsLoc);

        this.wmGenericDao.update(tlMsLoc);
        this.wmGenericDao.refresh(tlMsLoc);

        return tlMsLoc;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsLoc partialUpdate(Long tlmslocId, Map<String, Object>tlMsLocPatch) {
        LOGGER.debug("Partially Updating the TlMsLoc with id: {}", tlmslocId);

        TlMsLoc tlMsLoc = getById(tlmslocId);

        try {
            ObjectReader tlMsLocReader = this.objectMapper.reader().forType(TlMsLoc.class).withValueToUpdate(tlMsLoc);
            tlMsLoc = tlMsLocReader.readValue(this.objectMapper.writeValueAsString(tlMsLocPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsLocPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsLoc = update(tlMsLoc);

        return tlMsLoc;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsLoc delete(Long tlmslocId) {
        LOGGER.debug("Deleting TlMsLoc with id: {}", tlmslocId);
        TlMsLoc deleted = this.wmGenericDao.findById(tlmslocId);
        if (deleted == null) {
            LOGGER.debug("No TlMsLoc found with id: {}", tlmslocId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsLoc.class.getSimpleName(), tlmslocId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsLoc tlMsLoc) {
        LOGGER.debug("Deleting TlMsLoc with {}", tlMsLoc);
        this.wmGenericDao.delete(tlMsLoc);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsLoc> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsLocs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsLoc> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsLocs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsLoc to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsLoc to {} format", options.getExportType());
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