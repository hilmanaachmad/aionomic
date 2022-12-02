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

import id.co.aio.procure_to_pay.avm2.TlHrAg;


/**
 * ServiceImpl object for domain model class TlHrAg.
 *
 * @see TlHrAg
 */
@Service("AVM2.TlHrAgService")
@Validated
@EntityService(entityClass = TlHrAg.class, serviceId = "AVM2")
public class TlHrAgServiceImpl implements TlHrAgService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlHrAgServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlHrAgDao")
    private WMGenericDao<TlHrAg, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlHrAg, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlHrAg create(TlHrAg tlHrAg) {
        LOGGER.debug("Creating a new TlHrAg with information: {}", tlHrAg);

        TlHrAg tlHrAgCreated = this.wmGenericDao.create(tlHrAg);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlHrAgCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlHrAg getById(Integer tlhragId) {
        LOGGER.debug("Finding TlHrAg by id: {}", tlhragId);
        return this.wmGenericDao.findById(tlhragId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlHrAg findById(Integer tlhragId) {
        LOGGER.debug("Finding TlHrAg by id: {}", tlhragId);
        try {
            return this.wmGenericDao.findById(tlhragId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlHrAg found with id: {}", tlhragId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlHrAg> findByMultipleIds(List<Integer> tlhragIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlHrAgs by ids: {}", tlhragIds);

        return this.wmGenericDao.findByMultipleIds(tlhragIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlHrAg update(TlHrAg tlHrAg) {
        LOGGER.debug("Updating TlHrAg with information: {}", tlHrAg);

        this.wmGenericDao.update(tlHrAg);
        this.wmGenericDao.refresh(tlHrAg);

        return tlHrAg;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlHrAg partialUpdate(Integer tlhragId, Map<String, Object>tlHrAgPatch) {
        LOGGER.debug("Partially Updating the TlHrAg with id: {}", tlhragId);

        TlHrAg tlHrAg = getById(tlhragId);

        try {
            ObjectReader tlHrAgReader = this.objectMapper.reader().forType(TlHrAg.class).withValueToUpdate(tlHrAg);
            tlHrAg = tlHrAgReader.readValue(this.objectMapper.writeValueAsString(tlHrAgPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlHrAgPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlHrAg = update(tlHrAg);

        return tlHrAg;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlHrAg delete(Integer tlhragId) {
        LOGGER.debug("Deleting TlHrAg with id: {}", tlhragId);
        TlHrAg deleted = this.wmGenericDao.findById(tlhragId);
        if (deleted == null) {
            LOGGER.debug("No TlHrAg found with id: {}", tlhragId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlHrAg.class.getSimpleName(), tlhragId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlHrAg tlHrAg) {
        LOGGER.debug("Deleting TlHrAg with {}", tlHrAg);
        this.wmGenericDao.delete(tlHrAg);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlHrAg> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlHrAgs");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlHrAg> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlHrAgs");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlHrAg to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlHrAg to {} format", options.getExportType());
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