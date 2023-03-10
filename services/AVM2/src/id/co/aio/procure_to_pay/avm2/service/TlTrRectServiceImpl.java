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

import id.co.aio.procure_to_pay.avm2.TlTrRect;


/**
 * ServiceImpl object for domain model class TlTrRect.
 *
 * @see TlTrRect
 */
@Service("AVM2.TlTrRectService")
@Validated
@EntityService(entityClass = TlTrRect.class, serviceId = "AVM2")
public class TlTrRectServiceImpl implements TlTrRectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TlTrRectServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.TlTrRectDao")
    private WMGenericDao<TlTrRect, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TlTrRect, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlTrRect create(TlTrRect tlTrRect) {
        LOGGER.debug("Creating a new TlTrRect with information: {}", tlTrRect);

        TlTrRect tlTrRectCreated = this.wmGenericDao.create(tlTrRect);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tlTrRectCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlTrRect getById(Integer tltrrectId) {
        LOGGER.debug("Finding TlTrRect by id: {}", tltrrectId);
        return this.wmGenericDao.findById(tltrrectId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public TlTrRect findById(Integer tltrrectId) {
        LOGGER.debug("Finding TlTrRect by id: {}", tltrrectId);
        try {
            return this.wmGenericDao.findById(tltrrectId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TlTrRect found with id: {}", tltrrectId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<TlTrRect> findByMultipleIds(List<Integer> tltrrectIds, boolean orderedReturn) {
        LOGGER.debug("Finding TlTrRects by ids: {}", tltrrectIds);

        return this.wmGenericDao.findByMultipleIds(tltrrectIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public TlTrRect update(TlTrRect tlTrRect) {
        LOGGER.debug("Updating TlTrRect with information: {}", tlTrRect);

        this.wmGenericDao.update(tlTrRect);
        this.wmGenericDao.refresh(tlTrRect);

        return tlTrRect;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlTrRect partialUpdate(Integer tltrrectId, Map<String, Object>tlTrRectPatch) {
        LOGGER.debug("Partially Updating the TlTrRect with id: {}", tltrrectId);

        TlTrRect tlTrRect = getById(tltrrectId);

        try {
            ObjectReader tlTrRectReader = this.objectMapper.reader().forType(TlTrRect.class).withValueToUpdate(tlTrRect);
            tlTrRect = tlTrRectReader.readValue(this.objectMapper.writeValueAsString(tlTrRectPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tlTrRectPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tlTrRect = update(tlTrRect);

        return tlTrRect;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public TlTrRect delete(Integer tltrrectId) {
        LOGGER.debug("Deleting TlTrRect with id: {}", tltrrectId);
        TlTrRect deleted = this.wmGenericDao.findById(tltrrectId);
        if (deleted == null) {
            LOGGER.debug("No TlTrRect found with id: {}", tltrrectId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TlTrRect.class.getSimpleName(), tltrrectId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(TlTrRect tlTrRect) {
        LOGGER.debug("Deleting TlTrRect with {}", tlTrRect);
        this.wmGenericDao.delete(tlTrRect);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlTrRect> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TlTrRects");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<TlTrRect> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TlTrRects");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table TlTrRect to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table TlTrRect to {} format", options.getExportType());
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