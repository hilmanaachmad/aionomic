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

import id.co.aio.procure_to_pay.aio_ptp.TblTAnnouncement;


/**
 * ServiceImpl object for domain model class TblTAnnouncement.
 *
 * @see TblTAnnouncement
 */
@Service("aio_ptp.TblTAnnouncementService")
@Validated
@EntityService(entityClass = TblTAnnouncement.class, serviceId = "aio_ptp")
public class TblTAnnouncementServiceImpl implements TblTAnnouncementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblTAnnouncementServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblTAnnouncementDao")
    private WMGenericDao<TblTAnnouncement, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblTAnnouncement, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTAnnouncement create(TblTAnnouncement tblTannouncement) {
        LOGGER.debug("Creating a new TblTAnnouncement with information: {}", tblTannouncement);

        TblTAnnouncement tblTannouncementCreated = this.wmGenericDao.create(tblTannouncement);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblTannouncementCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTAnnouncement getById(Integer tbltannouncementId) {
        LOGGER.debug("Finding TblTAnnouncement by id: {}", tbltannouncementId);
        return this.wmGenericDao.findById(tbltannouncementId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblTAnnouncement findById(Integer tbltannouncementId) {
        LOGGER.debug("Finding TblTAnnouncement by id: {}", tbltannouncementId);
        try {
            return this.wmGenericDao.findById(tbltannouncementId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblTAnnouncement found with id: {}", tbltannouncementId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblTAnnouncement> findByMultipleIds(List<Integer> tbltannouncementIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblTAnnouncements by ids: {}", tbltannouncementIds);

        return this.wmGenericDao.findByMultipleIds(tbltannouncementIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblTAnnouncement update(TblTAnnouncement tblTannouncement) {
        LOGGER.debug("Updating TblTAnnouncement with information: {}", tblTannouncement);

        this.wmGenericDao.update(tblTannouncement);
        this.wmGenericDao.refresh(tblTannouncement);

        return tblTannouncement;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTAnnouncement partialUpdate(Integer tbltannouncementId, Map<String, Object>tblTannouncementPatch) {
        LOGGER.debug("Partially Updating the TblTAnnouncement with id: {}", tbltannouncementId);

        TblTAnnouncement tblTannouncement = getById(tbltannouncementId);

        try {
            ObjectReader tblTannouncementReader = this.objectMapper.reader().forType(TblTAnnouncement.class).withValueToUpdate(tblTannouncement);
            tblTannouncement = tblTannouncementReader.readValue(this.objectMapper.writeValueAsString(tblTannouncementPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblTannouncementPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblTannouncement = update(tblTannouncement);

        return tblTannouncement;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblTAnnouncement delete(Integer tbltannouncementId) {
        LOGGER.debug("Deleting TblTAnnouncement with id: {}", tbltannouncementId);
        TblTAnnouncement deleted = this.wmGenericDao.findById(tbltannouncementId);
        if (deleted == null) {
            LOGGER.debug("No TblTAnnouncement found with id: {}", tbltannouncementId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblTAnnouncement.class.getSimpleName(), tbltannouncementId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblTAnnouncement tblTannouncement) {
        LOGGER.debug("Deleting TblTAnnouncement with {}", tblTannouncement);
        this.wmGenericDao.delete(tblTannouncement);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTAnnouncement> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblTAnnouncements");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblTAnnouncement> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblTAnnouncements");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTAnnouncement to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblTAnnouncement to {} format", options.getExportType());
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