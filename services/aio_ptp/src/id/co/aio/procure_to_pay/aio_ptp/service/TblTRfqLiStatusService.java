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

import id.co.aio.procure_to_pay.aio_ptp.TblTRfqLiStatus;
import id.co.aio.procure_to_pay.aio_ptp.TblTRfqVenQuotation;

/**
 * Service object for domain model class {@link TblTRfqLiStatus}.
 */
public interface TblTRfqLiStatusService {

    /**
     * Creates a new TblTRfqLiStatus. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTRfqLiStatus if any.
     *
     * @param tblTrfqLiStatus Details of the TblTRfqLiStatus to be created; value cannot be null.
     * @return The newly created TblTRfqLiStatus.
     */
    TblTRfqLiStatus create(@Valid TblTRfqLiStatus tblTrfqLiStatus);


	/**
     * Returns TblTRfqLiStatus by given id if exists.
     *
     * @param tbltrfqlistatusId The id of the TblTRfqLiStatus to get; value cannot be null.
     * @return TblTRfqLiStatus associated with the given tbltrfqlistatusId.
	 * @throws EntityNotFoundException If no TblTRfqLiStatus is found.
     */
    TblTRfqLiStatus getById(Integer tbltrfqlistatusId);

    /**
     * Find and return the TblTRfqLiStatus by given id if exists, returns null otherwise.
     *
     * @param tbltrfqlistatusId The id of the TblTRfqLiStatus to get; value cannot be null.
     * @return TblTRfqLiStatus associated with the given tbltrfqlistatusId.
     */
    TblTRfqLiStatus findById(Integer tbltrfqlistatusId);

	/**
     * Find and return the list of TblTRfqLiStatuses by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tbltrfqlistatusIds The id's of the TblTRfqLiStatus to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblTRfqLiStatuses associated with the given tbltrfqlistatusIds.
     */
    List<TblTRfqLiStatus> findByMultipleIds(List<Integer> tbltrfqlistatusIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblTRfqLiStatus. It replaces all fields of the existing TblTRfqLiStatus with the given tblTrfqLiStatus.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTRfqLiStatus if any.
     *
     * @param tblTrfqLiStatus The details of the TblTRfqLiStatus to be updated; value cannot be null.
     * @return The updated TblTRfqLiStatus.
     * @throws EntityNotFoundException if no TblTRfqLiStatus is found with given input.
     */
    TblTRfqLiStatus update(@Valid TblTRfqLiStatus tblTrfqLiStatus);


    /**
     * Partially updates the details of an existing TblTRfqLiStatus. It updates only the
     * fields of the existing TblTRfqLiStatus which are passed in the tblTrfqLiStatusPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTRfqLiStatus if any.
     *
     * @param tbltrfqlistatusId The id of the TblTRfqLiStatus to be deleted; value cannot be null.
     * @param tblTrfqLiStatusPatch The partial data of TblTRfqLiStatus which is supposed to be updated; value cannot be null.
     * @return The updated TblTRfqLiStatus.
     * @throws EntityNotFoundException if no TblTRfqLiStatus is found with given input.
     */
    TblTRfqLiStatus partialUpdate(Integer tbltrfqlistatusId, Map<String, Object> tblTrfqLiStatusPatch);

    /**
     * Deletes an existing TblTRfqLiStatus with the given id.
     *
     * @param tbltrfqlistatusId The id of the TblTRfqLiStatus to be deleted; value cannot be null.
     * @return The deleted TblTRfqLiStatus.
     * @throws EntityNotFoundException if no TblTRfqLiStatus found with the given id.
     */
    TblTRfqLiStatus delete(Integer tbltrfqlistatusId);

    /**
     * Deletes an existing TblTRfqLiStatus with the given object.
     *
     * @param tblTrfqLiStatus The instance of the TblTRfqLiStatus to be deleted; value cannot be null.
     */
    void delete(TblTRfqLiStatus tblTrfqLiStatus);

    /**
     * Find all TblTRfqLiStatuses matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTRfqLiStatuses.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblTRfqLiStatus> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblTRfqLiStatuses matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTRfqLiStatuses.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTRfqLiStatus> findAll(String query, Pageable pageable);

    /**
     * Exports all TblTRfqLiStatuses matching the given input query to the given exportType format.
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
     * Exports all TblTRfqLiStatuses matching the given input query to the given exportType format.
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
     * Retrieve the count of the TblTRfqLiStatuses in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblTRfqLiStatus.
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

    /*
     * Returns the associated tblTrfqVenQuotations for given TblTRfqLiStatus id.
     *
     * @param rlsId value of rlsId; value cannot be null
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of associated TblTRfqVenQuotation instances.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTRfqVenQuotation> findAssociatedTblTrfqVenQuotations(Integer rlsId, Pageable pageable);

}