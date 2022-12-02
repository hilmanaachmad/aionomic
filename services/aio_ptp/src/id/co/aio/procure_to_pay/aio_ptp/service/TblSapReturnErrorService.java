/*Copyright (c) 2020-2021 aio.co.id All Rights Reserved.
 This software is the confidential and proprietary information of aio.co.id You shall not disclose such Confidential Information and shall use it only in accordance
 with the terms of the source code license agreement you entered into with aio.co.id*/
package id.co.aio.procure_to_pay.aio_ptp.service;

/*This is a Studio Managed File. DO NOT EDIT THIS FILE. Your changes may be reverted by Studio.*/

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.wavemaker.runtime.data.exception.EntityNotFoundException;
import com.wavemaker.runtime.data.export.DataExportOptions;
import com.wavemaker.runtime.data.export.ExportType;
import com.wavemaker.runtime.data.expression.QueryFilter;
import com.wavemaker.runtime.data.model.AggregationInfo;
import com.wavemaker.runtime.file.model.Downloadable;

import id.co.aio.procure_to_pay.aio_ptp.TblSapReturnError;

/**
 * Service object for domain model class {@link TblSapReturnError}.
 */
public interface TblSapReturnErrorService {

    /**
     * Creates a new TblSapReturnError. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblSapReturnError if any.
     *
     * @param tblSapReturnError Details of the TblSapReturnError to be created; value cannot be null.
     * @return The newly created TblSapReturnError.
     */
    TblSapReturnError create(@Valid TblSapReturnError tblSapReturnError);


	/**
     * Returns TblSapReturnError by given id if exists.
     *
     * @param tblsapreturnerrorId The id of the TblSapReturnError to get; value cannot be null.
     * @return TblSapReturnError associated with the given tblsapreturnerrorId.
	 * @throws EntityNotFoundException If no TblSapReturnError is found.
     */
    TblSapReturnError getById(Integer tblsapreturnerrorId);

    /**
     * Find and return the TblSapReturnError by given id if exists, returns null otherwise.
     *
     * @param tblsapreturnerrorId The id of the TblSapReturnError to get; value cannot be null.
     * @return TblSapReturnError associated with the given tblsapreturnerrorId.
     */
    TblSapReturnError findById(Integer tblsapreturnerrorId);

	/**
     * Find and return the list of TblSapReturnErrors by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tblsapreturnerrorIds The id's of the TblSapReturnError to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblSapReturnErrors associated with the given tblsapreturnerrorIds.
     */
    List<TblSapReturnError> findByMultipleIds(List<Integer> tblsapreturnerrorIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblSapReturnError. It replaces all fields of the existing TblSapReturnError with the given tblSapReturnError.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblSapReturnError if any.
     *
     * @param tblSapReturnError The details of the TblSapReturnError to be updated; value cannot be null.
     * @return The updated TblSapReturnError.
     * @throws EntityNotFoundException if no TblSapReturnError is found with given input.
     */
    TblSapReturnError update(@Valid TblSapReturnError tblSapReturnError);


    /**
     * Partially updates the details of an existing TblSapReturnError. It updates only the
     * fields of the existing TblSapReturnError which are passed in the tblSapReturnErrorPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblSapReturnError if any.
     *
     * @param tblsapreturnerrorId The id of the TblSapReturnError to be deleted; value cannot be null.
     * @param tblSapReturnErrorPatch The partial data of TblSapReturnError which is supposed to be updated; value cannot be null.
     * @return The updated TblSapReturnError.
     * @throws EntityNotFoundException if no TblSapReturnError is found with given input.
     */
    TblSapReturnError partialUpdate(Integer tblsapreturnerrorId, Map<String, Object> tblSapReturnErrorPatch);

    /**
     * Deletes an existing TblSapReturnError with the given id.
     *
     * @param tblsapreturnerrorId The id of the TblSapReturnError to be deleted; value cannot be null.
     * @return The deleted TblSapReturnError.
     * @throws EntityNotFoundException if no TblSapReturnError found with the given id.
     */
    TblSapReturnError delete(Integer tblsapreturnerrorId);

    /**
     * Deletes an existing TblSapReturnError with the given object.
     *
     * @param tblSapReturnError The instance of the TblSapReturnError to be deleted; value cannot be null.
     */
    void delete(TblSapReturnError tblSapReturnError);

    /**
     * Find all TblSapReturnErrors matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblSapReturnErrors.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblSapReturnError> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblSapReturnErrors matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblSapReturnErrors.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblSapReturnError> findAll(String query, Pageable pageable);

    /**
     * Exports all TblSapReturnErrors matching the given input query to the given exportType format.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param exportType The format in which to export the data; value cannot be null.
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return The Downloadable file in given export type.
     *
     * @see Pageable
     * @see ExportType
     * @see Downloadable
     */
    Downloadable export(ExportType exportType, String query, Pageable pageable);

    /**
     * Exports all TblSapReturnErrors matching the given input query to the given exportType format.
     *
     * @param options The export options provided by the user; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @param outputStream The output stream of the file for the exported data to be written to.
     *
     * @see DataExportOptions
     * @see Pageable
     * @see OutputStream
     */
    void export(DataExportOptions options, Pageable pageable, OutputStream outputStream);

    /**
     * Retrieve the count of the TblSapReturnErrors in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblSapReturnError.
     */
    long count(String query);

    /**
     * Retrieve aggregated values with matching aggregation info.
     *
     * @param aggregationInfo info related to aggregations.
     * @param pageable Details of the pagination information along with the sorting options. If null exports all matching records.
     * @return Paginated data with included fields.
     *
     * @see AggregationInfo
     * @see Pageable
     * @see Page
	 */
    Page<Map<String, Object>> getAggregatedValues(AggregationInfo aggregationInfo, Pageable pageable);


}