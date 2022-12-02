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

import id.co.aio.procure_to_pay.aio_ptp.TblMDoctype;


/**
 * ServiceImpl object for domain model class TblMDoctype.
 *
 * @see TblMDoctype
 */
@Service("aio_ptp.TblMDoctypeService")
@Validated
@EntityService(entityClass = TblMDoctype.class, serviceId = "aio_ptp")
public class TblMDoctypeServiceImpl implements TblMDoctypeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMDoctypeServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMDoctypeDao")
    private WMGenericDao<TblMDoctype, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMDoctype, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDoctype create(TblMDoctype tblMdoctype) {
        LOGGER.debug("Creating a new TblMDoctype with information: {}", tblMdoctype);

        TblMDoctype tblMdoctypeCreated = this.wmGenericDao.create(tblMdoctype);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMdoctypeCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMDoctype getById(Integer tblmdoctypeId) {
        LOGGER.debug("Finding TblMDoctype by id: {}", tblmdoctypeId);
        return this.wmGenericDao.findById(tblmdoctypeId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMDoctype findById(Integer tblmdoctypeId) {
        LOGGER.debug("Finding TblMDoctype by id: {}", tblmdoctypeId);
        try {
            return this.wmGenericDao.findById(tblmdoctypeId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMDoctype found with id: {}", tblmdoctypeId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMDoctype> findByMultipleIds(List<Integer> tblmdoctypeIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMDoctypes by ids: {}", tblmdoctypeIds);

        return this.wmGenericDao.findByMultipleIds(tblmdoctypeIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMDoctype update(TblMDoctype tblMdoctype) {
        LOGGER.debug("Updating TblMDoctype with information: {}", tblMdoctype);

        this.wmGenericDao.update(tblMdoctype);
        this.wmGenericDao.refresh(tblMdoctype);

        return tblMdoctype;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDoctype partialUpdate(Integer tblmdoctypeId, Map<String, Object>tblMdoctypePatch) {
        LOGGER.debug("Partially Updating the TblMDoctype with id: {}", tblmdoctypeId);

        TblMDoctype tblMdoctype = getById(tblmdoctypeId);

        try {
            ObjectReader tblMdoctypeReader = this.objectMapper.reader().forType(TblMDoctype.class).withValueToUpdate(tblMdoctype);
            tblMdoctype = tblMdoctypeReader.readValue(this.objectMapper.writeValueAsString(tblMdoctypePatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMdoctypePatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMdoctype = update(tblMdoctype);

        return tblMdoctype;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDoctype delete(Integer tblmdoctypeId) {
        LOGGER.debug("Deleting TblMDoctype with id: {}", tblmdoctypeId);
        TblMDoctype deleted = this.wmGenericDao.findById(tblmdoctypeId);
        if (deleted == null) {
            LOGGER.debug("No TblMDoctype found with id: {}", tblmdoctypeId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMDoctype.class.getSimpleName(), tblmdoctypeId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMDoctype tblMdoctype) {
        LOGGER.debug("Deleting TblMDoctype with {}", tblMdoctype);
        this.wmGenericDao.delete(tblMdoctype);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMDoctype> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMDoctypes");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMDoctype> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMDoctypes");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMDoctype to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMDoctype to {} format", options.getExportType());
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