/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_employee.service;

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

import id.co.aio.procure_to_pay.aio_employee.VEmployeeActive;


/**
 * ServiceImpl object for domain model class VEmployeeActive.
 *
 * @see VEmployeeActive
 */
@Service("aio_employee.VEmployeeActiveService")
@Validated
@EntityService(entityClass = VEmployeeActive.class, serviceId = "aio_employee")
public class VEmployeeActiveServiceImpl implements VEmployeeActiveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VEmployeeActiveServiceImpl.class);


    @Autowired
    @Qualifier("aio_employee.VEmployeeActiveDao")
    private WMGenericDao<VEmployeeActive, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<VEmployeeActive, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public VEmployeeActive create(VEmployeeActive vemployeeActive) {
        LOGGER.debug("Creating a new VEmployeeActive with information: {}", vemployeeActive);

        VEmployeeActive vemployeeActiveCreated = this.wmGenericDao.create(vemployeeActive);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(vemployeeActiveCreated);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public VEmployeeActive getById(Integer vemployeeactiveId) {
        LOGGER.debug("Finding VEmployeeActive by id: {}", vemployeeactiveId);
        return this.wmGenericDao.findById(vemployeeactiveId);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public VEmployeeActive findById(Integer vemployeeactiveId) {
        LOGGER.debug("Finding VEmployeeActive by id: {}", vemployeeactiveId);
        try {
            return this.wmGenericDao.findById(vemployeeactiveId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No VEmployeeActive found with id: {}", vemployeeactiveId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public List<VEmployeeActive> findByMultipleIds(List<Integer> vemployeeactiveIds, boolean orderedReturn) {
        LOGGER.debug("Finding VEmployeeActives by ids: {}", vemployeeactiveIds);

        return this.wmGenericDao.findByMultipleIds(vemployeeactiveIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_employeeTransactionManager")
    @Override
    public VEmployeeActive update(VEmployeeActive vemployeeActive) {
        LOGGER.debug("Updating VEmployeeActive with information: {}", vemployeeActive);

        this.wmGenericDao.update(vemployeeActive);
        this.wmGenericDao.refresh(vemployeeActive);

        return vemployeeActive;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public VEmployeeActive partialUpdate(Integer vemployeeactiveId, Map<String, Object>vemployeeActivePatch) {
        LOGGER.debug("Partially Updating the VEmployeeActive with id: {}", vemployeeactiveId);

        VEmployeeActive vemployeeActive = getById(vemployeeactiveId);

        try {
            ObjectReader vemployeeActiveReader = this.objectMapper.reader().forType(VEmployeeActive.class).withValueToUpdate(vemployeeActive);
            vemployeeActive = vemployeeActiveReader.readValue(this.objectMapper.writeValueAsString(vemployeeActivePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", vemployeeActivePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        vemployeeActive = update(vemployeeActive);

        return vemployeeActive;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public VEmployeeActive delete(Integer vemployeeactiveId) {
        LOGGER.debug("Deleting VEmployeeActive with id: {}", vemployeeactiveId);
        VEmployeeActive deleted = this.wmGenericDao.findById(vemployeeactiveId);
        if (deleted == null) {
            LOGGER.debug("No VEmployeeActive found with id: {}", vemployeeactiveId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), VEmployeeActive.class.getSimpleName(), vemployeeactiveId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public void delete(VEmployeeActive vemployeeActive) {
        LOGGER.debug("Deleting VEmployeeActive with {}", vemployeeActive);
        this.wmGenericDao.delete(vemployeeActive);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public Page<VEmployeeActive> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all VEmployeeActives");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public Page<VEmployeeActive> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all VEmployeeActives");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_employee for table VEmployeeActive to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_employee for table VEmployeeActive to {} format", options.getExportType());
        this.wmGenericDao.export(options, pageable, outputStream);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public long count(String query) {
        return this.wmGenericDao.count(query);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable) {
        return this.wmGenericDao.getAggregatedValues(aggregationInfo, pageable);
    }



}