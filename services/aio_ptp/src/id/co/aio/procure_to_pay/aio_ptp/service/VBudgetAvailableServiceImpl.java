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

import id.co.aio.procure_to_pay.aio_ptp.VBudgetAvailable;


/**
 * ServiceImpl object for domain model class VBudgetAvailable.
 *
 * @see VBudgetAvailable
 */
@Service("aio_ptp.VBudgetAvailableService")
@Validated
@EntityService(entityClass = VBudgetAvailable.class, serviceId = "aio_ptp")
public class VBudgetAvailableServiceImpl implements VBudgetAvailableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VBudgetAvailableServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.VBudgetAvailableDao")
    private WMGenericDao<VBudgetAvailable, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VBudgetAvailable, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VBudgetAvailable create(VBudgetAvailable vbudgetAvailable) {
        LOGGER.debug("Creating a new VBudgetAvailable with information: {}", vbudgetAvailable);

        VBudgetAvailable vbudgetAvailableCreated = this.wmGenericDao.create(vbudgetAvailable);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vbudgetAvailableCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VBudgetAvailable getById(Integer vbudgetavailableId) {
        LOGGER.debug("Finding VBudgetAvailable by id: {}", vbudgetavailableId);
        return this.wmGenericDao.findById(vbudgetavailableId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public VBudgetAvailable findById(Integer vbudgetavailableId) {
        LOGGER.debug("Finding VBudgetAvailable by id: {}", vbudgetavailableId);
        try {
            return this.wmGenericDao.findById(vbudgetavailableId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VBudgetAvailable found with id: {}", vbudgetavailableId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<VBudgetAvailable> findByMultipleIds(List<Integer> vbudgetavailableIds, boolean orderedReturn) {
        LOGGER.debug("Finding VBudgetAvailables by ids: {}", vbudgetavailableIds);

        return this.wmGenericDao.findByMultipleIds(vbudgetavailableIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public VBudgetAvailable update(VBudgetAvailable vbudgetAvailable) {
        LOGGER.debug("Updating VBudgetAvailable with information: {}", vbudgetAvailable);

        this.wmGenericDao.update(vbudgetAvailable);
        this.wmGenericDao.refresh(vbudgetAvailable);

        return vbudgetAvailable;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VBudgetAvailable partialUpdate(Integer vbudgetavailableId, Map<String, Object>vbudgetAvailablePatch) {
        LOGGER.debug("Partially Updating the VBudgetAvailable with id: {}", vbudgetavailableId);

        VBudgetAvailable vbudgetAvailable = getById(vbudgetavailableId);

        try {
            ObjectReader vbudgetAvailableReader = this.objectMapper.reader().forType(VBudgetAvailable.class).withValueToUpdate(vbudgetAvailable);
            vbudgetAvailable = vbudgetAvailableReader.readValue(this.objectMapper.writeValueAsString(vbudgetAvailablePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vbudgetAvailablePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vbudgetAvailable = update(vbudgetAvailable);

        return vbudgetAvailable;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public VBudgetAvailable delete(Integer vbudgetavailableId) {
        LOGGER.debug("Deleting VBudgetAvailable with id: {}", vbudgetavailableId);
        VBudgetAvailable deleted = this.wmGenericDao.findById(vbudgetavailableId);
        if (deleted == null) {
            LOGGER.debug("No VBudgetAvailable found with id: {}", vbudgetavailableId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VBudgetAvailable.class.getSimpleName(), vbudgetavailableId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(VBudgetAvailable vbudgetAvailable) {
        LOGGER.debug("Deleting VBudgetAvailable with {}", vbudgetAvailable);
        this.wmGenericDao.delete(vbudgetAvailable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VBudgetAvailable> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VBudgetAvailables");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<VBudgetAvailable> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VBudgetAvailables");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table VBudgetAvailable to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table VBudgetAvailable to {} format", options.getExportType());
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