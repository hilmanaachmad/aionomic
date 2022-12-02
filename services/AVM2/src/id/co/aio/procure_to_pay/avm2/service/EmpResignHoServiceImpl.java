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

import id.co.aio.procure_to_pay.avm2.EmpResignHo;
import id.co.aio.procure_to_pay.avm2.EmpResignHoId;


/**
 * ServiceImpl object for domain model class EmpResignHo.
 *
 * @see EmpResignHo
 */
@Service("AVM2.EmpResignHoService")
@Validated
@EntityService(entityClass = EmpResignHo.class, serviceId = "AVM2")
public class EmpResignHoServiceImpl implements EmpResignHoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpResignHoServiceImpl.class);


    @Autowired
    @Qualifier("AVM2.EmpResignHoDao")
    private WMGenericDao<EmpResignHo, EmpResignHoId> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<EmpResignHo, EmpResignHoId> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public EmpResignHo create(EmpResignHo empResignHo) {
        LOGGER.debug("Creating a new EmpResignHo with information: {}", empResignHo);

        EmpResignHo empResignHoCreated = this.wmGenericDao.create(empResignHo);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(empResignHoCreated);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public EmpResignHo getById(EmpResignHoId empresignhoId) {
        LOGGER.debug("Finding EmpResignHo by id: {}", empresignhoId);
        return this.wmGenericDao.findById(empresignhoId);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public EmpResignHo findById(EmpResignHoId empresignhoId) {
        LOGGER.debug("Finding EmpResignHo by id: {}", empresignhoId);
        try {
            return this.wmGenericDao.findById(empresignhoId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No EmpResignHo found with id: {}", empresignhoId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public List<EmpResignHo> findByMultipleIds(List<EmpResignHoId> empresignhoIds, boolean orderedReturn) {
        LOGGER.debug("Finding EmpResignHos by ids: {}", empresignhoIds);

        return this.wmGenericDao.findByMultipleIds(empresignhoIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "AVM2TransactionManager")
    @Override
    public EmpResignHo update(EmpResignHo empResignHo) {
        LOGGER.debug("Updating EmpResignHo with information: {}", empResignHo);

        this.wmGenericDao.update(empResignHo);
        this.wmGenericDao.refresh(empResignHo);

        return empResignHo;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public EmpResignHo partialUpdate(EmpResignHoId empresignhoId, Map<String, Object>empResignHoPatch) {
        LOGGER.debug("Partially Updating the EmpResignHo with id: {}", empresignhoId);

        EmpResignHo empResignHo = getById(empresignhoId);

        try {
            ObjectReader empResignHoReader = this.objectMapper.reader().forType(EmpResignHo.class).withValueToUpdate(empResignHo);
            empResignHo = empResignHoReader.readValue(this.objectMapper.writeValueAsString(empResignHoPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", empResignHoPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        empResignHo = update(empResignHo);

        return empResignHo;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public EmpResignHo delete(EmpResignHoId empresignhoId) {
        LOGGER.debug("Deleting EmpResignHo with id: {}", empresignhoId);
        EmpResignHo deleted = this.wmGenericDao.findById(empresignhoId);
        if (deleted == null) {
            LOGGER.debug("No EmpResignHo found with id: {}", empresignhoId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), EmpResignHo.class.getSimpleName(), empresignhoId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "AVM2TransactionManager")
    @Override
    public void delete(EmpResignHo empResignHo) {
        LOGGER.debug("Deleting EmpResignHo with {}", empResignHo);
        this.wmGenericDao.delete(empResignHo);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<EmpResignHo> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all EmpResignHos");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager")
    @Override
    public Page<EmpResignHo> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all EmpResignHos");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service AVM2 for table EmpResignHo to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "AVM2TransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service AVM2 for table EmpResignHo to {} format", options.getExportType());
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