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

import id.co.aio.procure_to_pay.aio_ptp.TblMRfqParameter;


/**
 * ServiceImpl object for domain model class TblMRfqParameter.
 *
 * @see TblMRfqParameter
 */
@Service("aio_ptp.TblMRfqParameterService")
@Validated
@EntityService(entityClass = TblMRfqParameter.class, serviceId = "aio_ptp")
public class TblMRfqParameterServiceImpl implements TblMRfqParameterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMRfqParameterServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMRfqParameterDao")
    private WMGenericDao<TblMRfqParameter, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMRfqParameter, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMRfqParameter create(TblMRfqParameter tblMrfqParameter) {
        LOGGER.debug("Creating a new TblMRfqParameter with information: {}", tblMrfqParameter);

        TblMRfqParameter tblMrfqParameterCreated = this.wmGenericDao.create(tblMrfqParameter);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMrfqParameterCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMRfqParameter getById(Integer tblmrfqparameterId) {
        LOGGER.debug("Finding TblMRfqParameter by id: {}", tblmrfqparameterId);
        return this.wmGenericDao.findById(tblmrfqparameterId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMRfqParameter findById(Integer tblmrfqparameterId) {
        LOGGER.debug("Finding TblMRfqParameter by id: {}", tblmrfqparameterId);
        try {
            return this.wmGenericDao.findById(tblmrfqparameterId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMRfqParameter found with id: {}", tblmrfqparameterId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMRfqParameter> findByMultipleIds(List<Integer> tblmrfqparameterIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMRfqParameters by ids: {}", tblmrfqparameterIds);

        return this.wmGenericDao.findByMultipleIds(tblmrfqparameterIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMRfqParameter update(TblMRfqParameter tblMrfqParameter) {
        LOGGER.debug("Updating TblMRfqParameter with information: {}", tblMrfqParameter);

        this.wmGenericDao.update(tblMrfqParameter);
        this.wmGenericDao.refresh(tblMrfqParameter);

        return tblMrfqParameter;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMRfqParameter partialUpdate(Integer tblmrfqparameterId, Map<String, Object>tblMrfqParameterPatch) {
        LOGGER.debug("Partially Updating the TblMRfqParameter with id: {}", tblmrfqparameterId);

        TblMRfqParameter tblMrfqParameter = getById(tblmrfqparameterId);

        try {
            ObjectReader tblMrfqParameterReader = this.objectMapper.reader().forType(TblMRfqParameter.class).withValueToUpdate(tblMrfqParameter);
            tblMrfqParameter = tblMrfqParameterReader.readValue(this.objectMapper.writeValueAsString(tblMrfqParameterPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMrfqParameterPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMrfqParameter = update(tblMrfqParameter);

        return tblMrfqParameter;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMRfqParameter delete(Integer tblmrfqparameterId) {
        LOGGER.debug("Deleting TblMRfqParameter with id: {}", tblmrfqparameterId);
        TblMRfqParameter deleted = this.wmGenericDao.findById(tblmrfqparameterId);
        if (deleted == null) {
            LOGGER.debug("No TblMRfqParameter found with id: {}", tblmrfqparameterId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMRfqParameter.class.getSimpleName(), tblmrfqparameterId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMRfqParameter tblMrfqParameter) {
        LOGGER.debug("Deleting TblMRfqParameter with {}", tblMrfqParameter);
        this.wmGenericDao.delete(tblMrfqParameter);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMRfqParameter> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMRfqParameters");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMRfqParameter> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMRfqParameters");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMRfqParameter to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMRfqParameter to {} format", options.getExportType());
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