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

import id.co.aio.procure_to_pay.aio_ptp.TblMDivisionBod;


/**
 * ServiceImpl object for domain model class TblMDivisionBod.
 *
 * @see TblMDivisionBod
 */
@Service("aio_ptp.TblMDivisionBodService")
@Validated
@EntityService(entityClass = TblMDivisionBod.class, serviceId = "aio_ptp")
public class TblMDivisionBodServiceImpl implements TblMDivisionBodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMDivisionBodServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMDivisionBodDao")
    private WMGenericDao<TblMDivisionBod, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMDivisionBod, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDivisionBod create(TblMDivisionBod tblMdivisionBod) {
        LOGGER.debug("Creating a new TblMDivisionBod with information: {}", tblMdivisionBod);

        TblMDivisionBod tblMdivisionBodCreated = this.wmGenericDao.create(tblMdivisionBod);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMdivisionBodCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMDivisionBod getById(Integer tblmdivisionbodId) {
        LOGGER.debug("Finding TblMDivisionBod by id: {}", tblmdivisionbodId);
        return this.wmGenericDao.findById(tblmdivisionbodId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMDivisionBod findById(Integer tblmdivisionbodId) {
        LOGGER.debug("Finding TblMDivisionBod by id: {}", tblmdivisionbodId);
        try {
            return this.wmGenericDao.findById(tblmdivisionbodId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMDivisionBod found with id: {}", tblmdivisionbodId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMDivisionBod> findByMultipleIds(List<Integer> tblmdivisionbodIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMDivisionBods by ids: {}", tblmdivisionbodIds);

        return this.wmGenericDao.findByMultipleIds(tblmdivisionbodIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMDivisionBod update(TblMDivisionBod tblMdivisionBod) {
        LOGGER.debug("Updating TblMDivisionBod with information: {}", tblMdivisionBod);

        this.wmGenericDao.update(tblMdivisionBod);
        this.wmGenericDao.refresh(tblMdivisionBod);

        return tblMdivisionBod;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDivisionBod partialUpdate(Integer tblmdivisionbodId, Map<String, Object>tblMdivisionBodPatch) {
        LOGGER.debug("Partially Updating the TblMDivisionBod with id: {}", tblmdivisionbodId);

        TblMDivisionBod tblMdivisionBod = getById(tblmdivisionbodId);

        try {
            ObjectReader tblMdivisionBodReader = this.objectMapper.reader().forType(TblMDivisionBod.class).withValueToUpdate(tblMdivisionBod);
            tblMdivisionBod = tblMdivisionBodReader.readValue(this.objectMapper.writeValueAsString(tblMdivisionBodPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMdivisionBodPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMdivisionBod = update(tblMdivisionBod);

        return tblMdivisionBod;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDivisionBod delete(Integer tblmdivisionbodId) {
        LOGGER.debug("Deleting TblMDivisionBod with id: {}", tblmdivisionbodId);
        TblMDivisionBod deleted = this.wmGenericDao.findById(tblmdivisionbodId);
        if (deleted == null) {
            LOGGER.debug("No TblMDivisionBod found with id: {}", tblmdivisionbodId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMDivisionBod.class.getSimpleName(), tblmdivisionbodId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMDivisionBod tblMdivisionBod) {
        LOGGER.debug("Deleting TblMDivisionBod with {}", tblMdivisionBod);
        this.wmGenericDao.delete(tblMdivisionBod);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMDivisionBod> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMDivisionBods");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMDivisionBod> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMDivisionBods");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMDivisionBod to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMDivisionBod to {} format", options.getExportType());
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