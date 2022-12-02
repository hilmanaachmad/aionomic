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

import id.co.aio.procure_to_pay.aio_ptp.TblTPoHistory;


/**
 * ServiceImpl object for domain model class TblTPoHistory.
 *
 * @see TblTPoHistory
 */
@Service("aio_ptp.TblTPoHistoryService")
@Validated
@EntityService(entityClass = TblTPoHistory.class, serviceId = "aio_ptp")
public class TblTPoHistoryServiceImpl implements TblTPoHistoryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTPoHistoryServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblTPoHistoryDao")
    private WMGenericDao<TblTPoHistory, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTPoHistory, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTPoHistory create(TblTPoHistory tblTpoHistory) {
        LOGGER.debug("Creating a new TblTPoHistory with information: {}", tblTpoHistory);

        TblTPoHistory tblTpoHistoryCreated = this.wmGenericDao.create(tblTpoHistory);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTpoHistoryCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTPoHistory getById(Integer tbltpohistoryId) {
        LOGGER.debug("Finding TblTPoHistory by id: {}", tbltpohistoryId);
        return this.wmGenericDao.findById(tbltpohistoryId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTPoHistory findById(Integer tbltpohistoryId) {
        LOGGER.debug("Finding TblTPoHistory by id: {}", tbltpohistoryId);
        try {
            return this.wmGenericDao.findById(tbltpohistoryId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTPoHistory found with id: {}", tbltpohistoryId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTPoHistory> findByMultipleIds(List<Integer> tbltpohistoryIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTPoHistories by ids: {}", tbltpohistoryIds);

        return this.wmGenericDao.findByMultipleIds(tbltpohistoryIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTPoHistory update(TblTPoHistory tblTpoHistory) {
        LOGGER.debug("Updating TblTPoHistory with information: {}", tblTpoHistory);

        this.wmGenericDao.update(tblTpoHistory);
        this.wmGenericDao.refresh(tblTpoHistory);

        return tblTpoHistory;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTPoHistory partialUpdate(Integer tbltpohistoryId, Map<String, Object>tblTpoHistoryPatch) {
        LOGGER.debug("Partially Updating the TblTPoHistory with id: {}", tbltpohistoryId);

        TblTPoHistory tblTpoHistory = getById(tbltpohistoryId);

        try {
            ObjectReader tblTpoHistoryReader = this.objectMapper.reader().forType(TblTPoHistory.class).withValueToUpdate(tblTpoHistory);
            tblTpoHistory = tblTpoHistoryReader.readValue(this.objectMapper.writeValueAsString(tblTpoHistoryPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTpoHistoryPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTpoHistory = update(tblTpoHistory);

        return tblTpoHistory;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTPoHistory delete(Integer tbltpohistoryId) {
        LOGGER.debug("Deleting TblTPoHistory with id: {}", tbltpohistoryId);
        TblTPoHistory deleted = this.wmGenericDao.findById(tbltpohistoryId);
        if (deleted == null) {
            LOGGER.debug("No TblTPoHistory found with id: {}", tbltpohistoryId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTPoHistory.class.getSimpleName(), tbltpohistoryId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTPoHistory tblTpoHistory) {
        LOGGER.debug("Deleting TblTPoHistory with {}", tblTpoHistory);
        this.wmGenericDao.delete(tblTpoHistory);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTPoHistory> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTPoHistories");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTPoHistory> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTPoHistories");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTPoHistory to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTPoHistory to {} format", options.getExportType());
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