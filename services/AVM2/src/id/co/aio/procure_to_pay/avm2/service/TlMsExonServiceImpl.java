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

import id.co.aio.procure_to_pay.avm2.TlMsExon;


/**
 * ServiceImpl object for domain model class TlMsExon.
 *
 * @see TlMsExon
 */
@Service("AVM2.TlMsExonService")
@Validated
@EntityService(entityClass = TlMsExon.class, serviceId = "AVM2")
public class TlMsExonServiceImpl implements TlMsExonService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlMsExonServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlMsExonDao")
    private WMGenericDao<TlMsExon, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlMsExon, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsExon create(TlMsExon tlMsExon) {
        LOGGER.debug("Creating a new TlMsExon with information: {}", tlMsExon);

        TlMsExon tlMsExonCreated = this.wmGenericDao.create(tlMsExon);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlMsExonCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsExon getById(Integer tlmsexonId) {
        LOGGER.debug("Finding TlMsExon by id: {}", tlmsexonId);
        return this.wmGenericDao.findById(tlmsexonId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlMsExon findById(Integer tlmsexonId) {
        LOGGER.debug("Finding TlMsExon by id: {}", tlmsexonId);
        try {
            return this.wmGenericDao.findById(tlmsexonId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlMsExon found with id: {}", tlmsexonId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlMsExon> findByMultipleIds(List<Integer> tlmsexonIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlMsExons by ids: {}", tlmsexonIds);

        return this.wmGenericDao.findByMultipleIds(tlmsexonIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlMsExon update(TlMsExon tlMsExon) {
        LOGGER.debug("Updating TlMsExon with information: {}", tlMsExon);

        this.wmGenericDao.update(tlMsExon);
        this.wmGenericDao.refresh(tlMsExon);

        return tlMsExon;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsExon partialUpdate(Integer tlmsexonId, Map<String, Object>tlMsExonPatch) {
        LOGGER.debug("Partially Updating the TlMsExon with id: {}", tlmsexonId);

        TlMsExon tlMsExon = getById(tlmsexonId);

        try {
            ObjectReader tlMsExonReader = this.objectMapper.reader().forType(TlMsExon.class).withValueToUpdate(tlMsExon);
            tlMsExon = tlMsExonReader.readValue(this.objectMapper.writeValueAsString(tlMsExonPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlMsExonPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlMsExon = update(tlMsExon);

        return tlMsExon;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlMsExon delete(Integer tlmsexonId) {
        LOGGER.debug("Deleting TlMsExon with id: {}", tlmsexonId);
        TlMsExon deleted = this.wmGenericDao.findById(tlmsexonId);
        if (deleted == null) {
            LOGGER.debug("No TlMsExon found with id: {}", tlmsexonId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlMsExon.class.getSimpleName(), tlmsexonId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlMsExon tlMsExon) {
        LOGGER.debug("Deleting TlMsExon with {}", tlMsExon);
        this.wmGenericDao.delete(tlMsExon);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsExon> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlMsExons");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlMsExon> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlMsExons");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsExon to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlMsExon to {} format", options.getExportType());
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