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

import id.co.aio.procure_to_pay.aio_ptp.TblMMatGroupCoa;


/**
 * ServiceImpl object for domain model class TblMMatGroupCoa.
 *
 * @see TblMMatGroupCoa
 */
@Service("aio_ptp.TblMMatGroupCoaService")
@Validated
@EntityService(entityClass = TblMMatGroupCoa.class, serviceId = "aio_ptp")
public class TblMMatGroupCoaServiceImpl implements TblMMatGroupCoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMMatGroupCoaServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMMatGroupCoaDao")
    private WMGenericDao<TblMMatGroupCoa, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMMatGroupCoa, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMMatGroupCoa create(TblMMatGroupCoa tblMmatGroupCoa) {
        LOGGER.debug("Creating a new TblMMatGroupCoa with information: {}", tblMmatGroupCoa);

        TblMMatGroupCoa tblMmatGroupCoaCreated = this.wmGenericDao.create(tblMmatGroupCoa);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMmatGroupCoaCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMMatGroupCoa getById(Integer tblmmatgroupcoaId) {
        LOGGER.debug("Finding TblMMatGroupCoa by id: {}", tblmmatgroupcoaId);
        return this.wmGenericDao.findById(tblmmatgroupcoaId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMMatGroupCoa findById(Integer tblmmatgroupcoaId) {
        LOGGER.debug("Finding TblMMatGroupCoa by id: {}", tblmmatgroupcoaId);
        try {
            return this.wmGenericDao.findById(tblmmatgroupcoaId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMMatGroupCoa found with id: {}", tblmmatgroupcoaId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMMatGroupCoa> findByMultipleIds(List<Integer> tblmmatgroupcoaIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMMatGroupCoas by ids: {}", tblmmatgroupcoaIds);

        return this.wmGenericDao.findByMultipleIds(tblmmatgroupcoaIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMMatGroupCoa update(TblMMatGroupCoa tblMmatGroupCoa) {
        LOGGER.debug("Updating TblMMatGroupCoa with information: {}", tblMmatGroupCoa);

        this.wmGenericDao.update(tblMmatGroupCoa);
        this.wmGenericDao.refresh(tblMmatGroupCoa);

        return tblMmatGroupCoa;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMMatGroupCoa partialUpdate(Integer tblmmatgroupcoaId, Map<String, Object>tblMmatGroupCoaPatch) {
        LOGGER.debug("Partially Updating the TblMMatGroupCoa with id: {}", tblmmatgroupcoaId);

        TblMMatGroupCoa tblMmatGroupCoa = getById(tblmmatgroupcoaId);

        try {
            ObjectReader tblMmatGroupCoaReader = this.objectMapper.reader().forType(TblMMatGroupCoa.class).withValueToUpdate(tblMmatGroupCoa);
            tblMmatGroupCoa = tblMmatGroupCoaReader.readValue(this.objectMapper.writeValueAsString(tblMmatGroupCoaPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMmatGroupCoaPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMmatGroupCoa = update(tblMmatGroupCoa);

        return tblMmatGroupCoa;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMMatGroupCoa delete(Integer tblmmatgroupcoaId) {
        LOGGER.debug("Deleting TblMMatGroupCoa with id: {}", tblmmatgroupcoaId);
        TblMMatGroupCoa deleted = this.wmGenericDao.findById(tblmmatgroupcoaId);
        if (deleted == null) {
            LOGGER.debug("No TblMMatGroupCoa found with id: {}", tblmmatgroupcoaId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMMatGroupCoa.class.getSimpleName(), tblmmatgroupcoaId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMMatGroupCoa tblMmatGroupCoa) {
        LOGGER.debug("Deleting TblMMatGroupCoa with {}", tblMmatGroupCoa);
        this.wmGenericDao.delete(tblMmatGroupCoa);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMMatGroupCoa> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMMatGroupCoas");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMMatGroupCoa> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMMatGroupCoas");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMMatGroupCoa to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMMatGroupCoa to {} format", options.getExportType());
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