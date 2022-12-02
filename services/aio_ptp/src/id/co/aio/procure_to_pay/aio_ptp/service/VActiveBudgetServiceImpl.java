/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

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

import id.co.aio.procure_to_pay.aio_ptp.VActiveBudget;


/**
 * ServiceImpl object for domain model class VActiveBudget.
 *
 * @see VActiveBudget
 */
@Service("aio_ptp.VActiveBudgetService")
@Validated
@EntityService(entityClass = VActiveBudget.class, serviceId = "aio_ptp")
public class VActiveBudgetServiceImpl implements VActiveBudgetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VActiveBudgetServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VActiveBudgetDao")
    private WMGenericDao<VActiveBudget, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VActiveBudget, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VActiveBudget create(VActiveBudget vactiveBudget) {
        LOGGER.debug("Creating a new VActiveBudget with information: {}", vactiveBudget);

        VActiveBudget vactiveBudgetCreated = this.wmGenericDao.create(vactiveBudget);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vactiveBudgetCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VActiveBudget getById(Integer vactivebudgetId) {
        LOGGER.debug("Finding VActiveBudget by id: {}", vactivebudgetId);
        return this.wmGenericDao.findById(vactivebudgetId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VActiveBudget findById(Integer vactivebudgetId) {
        LOGGER.debug("Finding VActiveBudget by id: {}", vactivebudgetId);
        try {
            return this.wmGenericDao.findById(vactivebudgetId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VActiveBudget found with id: {}", vactivebudgetId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VActiveBudget> findByMultipleIds(List<Integer> vactivebudgetIds, boolean orderedReturn) {
        LOGGER.debug("Finding VActiveBudgets by ids: {}", vactivebudgetIds);

        return this.wmGenericDao.findByMultipleIds(vactivebudgetIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VActiveBudget update(VActiveBudget vactiveBudget) {
        LOGGER.debug("Updating VActiveBudget with information: {}", vactiveBudget);

        this.wmGenericDao.update(vactiveBudget);
        this.wmGenericDao.refresh(vactiveBudget);

        return vactiveBudget;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VActiveBudget partialUpdate(Integer vactivebudgetId, Map<String, Object>vactiveBudgetPatch) {
        LOGGER.debug("Partially Updating the VActiveBudget with id: {}", vactivebudgetId);

        VActiveBudget vactiveBudget = getById(vactivebudgetId);

        try {
            ObjectReader vactiveBudgetReader = this.objectMapper.reader().forType(VActiveBudget.class).withValueToUpdate(vactiveBudget);
            vactiveBudget = vactiveBudgetReader.readValue(this.objectMapper.writeValueAsString(vactiveBudgetPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vactiveBudgetPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vactiveBudget = update(vactiveBudget);

        return vactiveBudget;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VActiveBudget delete(Integer vactivebudgetId) {
        LOGGER.debug("Deleting VActiveBudget with id: {}", vactivebudgetId);
        VActiveBudget deleted = this.wmGenericDao.findById(vactivebudgetId);
        if (deleted == null) {
            LOGGER.debug("No VActiveBudget found with id: {}", vactivebudgetId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VActiveBudget.class.getSimpleName(), vactivebudgetId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VActiveBudget vactiveBudget) {
        LOGGER.debug("Deleting VActiveBudget with {}", vactiveBudget);
        this.wmGenericDao.delete(vactiveBudget);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VActiveBudget> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VActiveBudgets");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VActiveBudget> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VActiveBudgets");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VActiveBudget to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VActiveBudget to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}