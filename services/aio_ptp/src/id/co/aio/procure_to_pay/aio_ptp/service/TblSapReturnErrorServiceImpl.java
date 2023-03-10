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

import id.co.aio.procure_to_pay.aio_ptp.TblSapReturnError;


/**
 * ServiceImpl object for domain model class TblSapReturnError.
 *
 * @see TblSapReturnError
 */
@Service("aio_ptp.TblSapReturnErrorService")
@Validated
@EntityService(entityClass = TblSapReturnError.class, serviceId = "aio_ptp")
public class TblSapReturnErrorServiceImpl implements TblSapReturnErrorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblSapReturnErrorServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblSapReturnErrorDao")
    private WMGenericDao<TblSapReturnError, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblSapReturnError, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblSapReturnError create(TblSapReturnError tblSapReturnError) {
        LOGGER.debug("Creating a new TblSapReturnError with information: {}", tblSapReturnError);

        TblSapReturnError tblSapReturnErrorCreated = this.wmGenericDao.create(tblSapReturnError);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblSapReturnErrorCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblSapReturnError getById(Integer tblsapreturnerrorId) {
        LOGGER.debug("Finding TblSapReturnError by id: {}", tblsapreturnerrorId);
        return this.wmGenericDao.findById(tblsapreturnerrorId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblSapReturnError findById(Integer tblsapreturnerrorId) {
        LOGGER.debug("Finding TblSapReturnError by id: {}", tblsapreturnerrorId);
        try {
            return this.wmGenericDao.findById(tblsapreturnerrorId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblSapReturnError found with id: {}", tblsapreturnerrorId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblSapReturnError> findByMultipleIds(List<Integer> tblsapreturnerrorIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblSapReturnErrors by ids: {}", tblsapreturnerrorIds);

        return this.wmGenericDao.findByMultipleIds(tblsapreturnerrorIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblSapReturnError update(TblSapReturnError tblSapReturnError) {
        LOGGER.debug("Updating TblSapReturnError with information: {}", tblSapReturnError);

        this.wmGenericDao.update(tblSapReturnError);
        this.wmGenericDao.refresh(tblSapReturnError);

        return tblSapReturnError;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblSapReturnError partialUpdate(Integer tblsapreturnerrorId, Map<String, Object>tblSapReturnErrorPatch) {
        LOGGER.debug("Partially Updating the TblSapReturnError with id: {}", tblsapreturnerrorId);

        TblSapReturnError tblSapReturnError = getById(tblsapreturnerrorId);

        try {
            ObjectReader tblSapReturnErrorReader = this.objectMapper.reader().forType(TblSapReturnError.class).withValueToUpdate(tblSapReturnError);
            tblSapReturnError = tblSapReturnErrorReader.readValue(this.objectMapper.writeValueAsString(tblSapReturnErrorPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblSapReturnErrorPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblSapReturnError = update(tblSapReturnError);

        return tblSapReturnError;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblSapReturnError delete(Integer tblsapreturnerrorId) {
        LOGGER.debug("Deleting TblSapReturnError with id: {}", tblsapreturnerrorId);
        TblSapReturnError deleted = this.wmGenericDao.findById(tblsapreturnerrorId);
        if (deleted == null) {
            LOGGER.debug("No TblSapReturnError found with id: {}", tblsapreturnerrorId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblSapReturnError.class.getSimpleName(), tblsapreturnerrorId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblSapReturnError tblSapReturnError) {
        LOGGER.debug("Deleting TblSapReturnError with {}", tblSapReturnError);
        this.wmGenericDao.delete(tblSapReturnError);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblSapReturnError> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblSapReturnErrors");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblSapReturnError> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblSapReturnErrors");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblSapReturnError to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblSapReturnError to {} format", options.getExportType());
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