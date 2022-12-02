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

import id.co.aio.procure_to_pay.avm2.TlMsDe;


/**
 * ServiceImpl object for domain model class TlMsDe.
 *
 * @see TlMsDe
 */
@Service("AVM2.TlMsDeService")
@Validated
@EntityService(entityClass = TlMsDe.class, serviceId = "AVM2")
public class TlMsDeServiceImpl implements TlMsDeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsDeServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsDeDao")
    private WMGenericDao<TlMsDe, Long> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsDe, Long> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsDe create(TlMsDe tlMsDe) {
        LOGGER.debug("Creating a new TlMsDe with information: {}", tlMsDe);

        TlMsDe tlMsDeCreated = this.wmGenericDao.create(tlMsDe);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsDeCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsDe getById(Long tlmsdeId) {
        LOGGER.debug("Finding TlMsDe by id: {}", tlmsdeId);
        return this.wmGenericDao.findById(tlmsdeId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsDe findById(Long tlmsdeId) {
        LOGGER.debug("Finding TlMsDe by id: {}", tlmsdeId);
        try {
            return this.wmGenericDao.findById(tlmsdeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsDe found with id: {}", tlmsdeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsDe> findByMultipleIds(List<Long> tlmsdeIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsDes by ids: {}", tlmsdeIds);

        return this.wmGenericDao.findByMultipleIds(tlmsdeIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsDe update(TlMsDe tlMsDe) {
        LOGGER.debug("Updating TlMsDe with information: {}", tlMsDe);

        this.wmGenericDao.update(tlMsDe);
        this.wmGenericDao.refresh(tlMsDe);

        return tlMsDe;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsDe partialUpdate(Long tlmsdeId, Map<String, Object>tlMsDePatch) {
        LOGGER.debug("Partially Updating the TlMsDe with id: {}", tlmsdeId);

        TlMsDe tlMsDe = getById(tlmsdeId);

        try {
            ObjectReader tlMsDeReader = this.objectMapper.reader().forType(TlMsDe.class).withValueToUpdate(tlMsDe);
            tlMsDe = tlMsDeReader.readValue(this.objectMapper.writeValueAsString(tlMsDePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsDePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsDe = update(tlMsDe);

        return tlMsDe;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsDe delete(Long tlmsdeId) {
        LOGGER.debug("Deleting TlMsDe with id: {}", tlmsdeId);
        TlMsDe deleted = this.wmGenericDao.findById(tlmsdeId);
        if (deleted == null) {
            LOGGER.debug("No TlMsDe found with id: {}", tlmsdeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsDe.class.getSimpleName(), tlmsdeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsDe tlMsDe) {
        LOGGER.debug("Deleting TlMsDe with {}", tlMsDe);
        this.wmGenericDao.delete(tlMsDe);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsDe> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsDes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsDe> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsDes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsDe to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsDe to {} format", options.getExportType());
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