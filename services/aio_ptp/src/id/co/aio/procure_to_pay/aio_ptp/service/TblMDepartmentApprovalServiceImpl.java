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

import id.co.aio.procure_to_pay.aio_ptp.TblMDepartmentApproval;


/**
 * ServiceImpl object for domain model class TblMDepartmentApproval.
 *
 * @see TblMDepartmentApproval
 */
@Service("aio_ptp.TblMDepartmentApprovalService")
@Validated
@EntityService(entityClass = TblMDepartmentApproval.class, serviceId = "aio_ptp")
public class TblMDepartmentApprovalServiceImpl implements TblMDepartmentApprovalService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TblMDepartmentApprovalServiceImpl.class);


    @Autowired
    @Qualifier("aio_ptp.TblMDepartmentApprovalDao")
    private WMGenericDao<TblMDepartmentApproval, Integer> wmGenericDao;

    @Autowired
    @Qualifier("wmAppObjectMapper")
    private ObjectMapper objectMapper;


    public void setWMGenericDao(WMGenericDao<TblMDepartmentApproval, Integer> wmGenericDao) {
        this.wmGenericDao = wmGenericDao;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDepartmentApproval create(TblMDepartmentApproval tblMdepartmentApproval) {
        LOGGER.debug("Creating a new TblMDepartmentApproval with information: {}", tblMdepartmentApproval);

        TblMDepartmentApproval tblMdepartmentApprovalCreated = this.wmGenericDao.create(tblMdepartmentApproval);
        // reloading object from database to get database defined & server defined values.
        return this.wmGenericDao.refresh(tblMdepartmentApprovalCreated);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMDepartmentApproval getById(Integer tblmdepartmentapprovalId) {
        LOGGER.debug("Finding TblMDepartmentApproval by id: {}", tblmdepartmentapprovalId);
        return this.wmGenericDao.findById(tblmdepartmentapprovalId);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public TblMDepartmentApproval findById(Integer tblmdepartmentapprovalId) {
        LOGGER.debug("Finding TblMDepartmentApproval by id: {}", tblmdepartmentapprovalId);
        try {
            return this.wmGenericDao.findById(tblmdepartmentapprovalId);
        } catch (EntityNotFoundException ex) {
            LOGGER.debug("No TblMDepartmentApproval found with id: {}", tblmdepartmentapprovalId, ex);
            return null;
        }
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public List<TblMDepartmentApproval> findByMultipleIds(List<Integer> tblmdepartmentapprovalIds, boolean orderedReturn) {
        LOGGER.debug("Finding TblMDepartmentApprovals by ids: {}", tblmdepartmentapprovalIds);

        return this.wmGenericDao.findByMultipleIds(tblmdepartmentapprovalIds, orderedReturn);
    }


    @Transactional(rollbackFor = EntityNotFoundException.class, value = "aio_ptpTransactionManager")
    @Override
    public TblMDepartmentApproval update(TblMDepartmentApproval tblMdepartmentApproval) {
        LOGGER.debug("Updating TblMDepartmentApproval with information: {}", tblMdepartmentApproval);

        this.wmGenericDao.update(tblMdepartmentApproval);
        this.wmGenericDao.refresh(tblMdepartmentApproval);

        return tblMdepartmentApproval;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDepartmentApproval partialUpdate(Integer tblmdepartmentapprovalId, Map<String, Object>tblMdepartmentApprovalPatch) {
        LOGGER.debug("Partially Updating the TblMDepartmentApproval with id: {}", tblmdepartmentapprovalId);

        TblMDepartmentApproval tblMdepartmentApproval = getById(tblmdepartmentapprovalId);

        try {
            ObjectReader tblMdepartmentApprovalReader = this.objectMapper.reader().forType(TblMDepartmentApproval.class).withValueToUpdate(tblMdepartmentApproval);
            tblMdepartmentApproval = tblMdepartmentApprovalReader.readValue(this.objectMapper.writeValueAsString(tblMdepartmentApprovalPatch));
        } catch (IOException ex) {
            LOGGER.debug("There was a problem in applying the patch: {}", tblMdepartmentApprovalPatch, ex);
            throw new InvalidInputException("Could not apply patch",ex);
        }

        tblMdepartmentApproval = update(tblMdepartmentApproval);

        return tblMdepartmentApproval;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public TblMDepartmentApproval delete(Integer tblmdepartmentapprovalId) {
        LOGGER.debug("Deleting TblMDepartmentApproval with id: {}", tblmdepartmentapprovalId);
        TblMDepartmentApproval deleted = this.wmGenericDao.findById(tblmdepartmentapprovalId);
        if (deleted == null) {
            LOGGER.debug("No TblMDepartmentApproval found with id: {}", tblmdepartmentapprovalId);
            throw new EntityNotFoundException(MessageResource.create("com.wavemaker.runtime.entity.not.found"), TblMDepartmentApproval.class.getSimpleName(), tblmdepartmentapprovalId);
        }
        this.wmGenericDao.delete(deleted);
        return deleted;
    }

    @Transactional(value = "aio_ptpTransactionManager")
    @Override
    public void delete(TblMDepartmentApproval tblMdepartmentApproval) {
        LOGGER.debug("Deleting TblMDepartmentApproval with {}", tblMdepartmentApproval);
        this.wmGenericDao.delete(tblMdepartmentApproval);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMDepartmentApproval> findAll(QueryFilter[] queryFilters, Pageable pageable) {
        LOGGER.debug("Finding all TblMDepartmentApprovals");
        return this.wmGenericDao.search(queryFilters, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager")
    @Override
    public Page<TblMDepartmentApproval> findAll(String query, Pageable pageable) {
        LOGGER.debug("Finding all TblMDepartmentApprovals");
        return this.wmGenericDao.searchByQuery(query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public Downloadable export(ExportType exportType, String query, Pageable pageable) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMDepartmentApproval to {} format", exportType);
        return this.wmGenericDao.export(exportType, query, pageable);
    }

    @Transactional(readOnly = true, value = "aio_ptpTransactionManager", timeout = 300)
    @Override
    public void export(DataExportOptions options, Pageable pageable, OutputStream outputStream) {
        LOGGER.debug("exporting data in the service aio_ptp for table TblMDepartmentApproval to {} format", options.getExportType());
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