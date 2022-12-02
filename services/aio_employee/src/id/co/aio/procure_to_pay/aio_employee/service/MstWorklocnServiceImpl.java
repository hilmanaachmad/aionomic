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

import id.co.aio.procure_to_pay.aio_employee.MstWorklocn;


/**
 * ServiceImpl object for domain model class MstWorklocn.
 *
 * @see MstWorklocn
 */
@Service("aio_employee.MstWorklocnService")
@Validated
@EntityService(entityClass = MstWorklocn.class, serviceId = "aio_employee")
public class MstWorklocnServiceImpl implements MstWorklocnService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MstWorklocnServiceImpl.class);


    @Autowired
    @Qualifier("aio_employee.MstWorklocnDao")
    private WMGenericDao<MstWorklocn, String> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<MstWorklocn, String> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public MstWorklocn create(MstWorklocn mstWorklocn) {
        LOGGER.debug("Creating a new MstWorklocn with information: {}", mstWorklocn);

        MstWorklocn mstWorklocnCreated = this.wmGenericDao.create(mstWorklocn);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(mstWorklocnCreated);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public MstWorklocn getById(String mstworklocnId) {
        LOGGER.debug("Finding MstWorklocn by id: {}", mstworklocnId);
        return this.wmGenericDao.findById(mstworklocnId);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public MstWorklocn findById(String mstworklocnId) {
        LOGGER.debug("Finding MstWorklocn by id: {}", mstworklocnId);
        try {
            return this.wmGenericDao.findById(mstworklocnId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No MstWorklocn found with id: {}", mstworklocnId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public List<MstWorklocn> findByMultipleIds(List<String> mstworklocnIds, boolean orderedReturn) {
        LOGGER.debug("Finding MstWorklocns by ids: {}", mstworklocnIds);

        return this.wmGenericDao.findByMultipleIds(mstworklocnIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_employeeTransactionManager")
    @Override
    public MstWorklocn update(MstWorklocn mstWorklocn) {
        LOGGER.debug("Updating MstWorklocn with information: {}", mstWorklocn);

        this.wmGenericDao.update(mstWorklocn);
        this.wmGenericDao.refresh(mstWorklocn);

        return mstWorklocn;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public MstWorklocn partialUpdate(String mstworklocnId, Map<String, Object>mstWorklocnPatch) {
        LOGGER.debug("Partially Updating the MstWorklocn with id: {}", mstworklocnId);

        MstWorklocn mstWorklocn = getById(mstworklocnId);

        try {
            ObjectReader mstWorklocnReader = this.objectMapper.reader().forType(MstWorklocn.class).withValueToUpdate(mstWorklocn);
            mstWorklocn = mstWorklocnReader.readValue(this.objectMapper.writeValueAsString(mstWorklocnPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", mstWorklocnPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        mstWorklocn = update(mstWorklocn);

        return mstWorklocn;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public MstWorklocn delete(String mstworklocnId) {
        LOGGER.debug("Deleting MstWorklocn with id: {}", mstworklocnId);
        MstWorklocn deleted = this.wmGenericDao.findById(mstworklocnId);
        if (deleted == null) {
            LOGGER.debug("No MstWorklocn found with id: {}", mstworklocnId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), MstWorklocn.class.getSimpleName(), mstworklocnId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_employeeTransactionManager")
    @Override
    public void delete(MstWorklocn mstWorklocn) {
        LOGGER.debug("Deleting MstWorklocn with {}", mstWorklocn);
        this.wmGenericDao.delete(mstWorklocn);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public Page<MstWorklocn> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all MstWorklocns");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager")
    @Override
    public Page<MstWorklocn> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all MstWorklocns");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_employee for table MstWorklocn to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_employeeTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_employee for table MstWorklocn to {} format", options.getExportType());
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