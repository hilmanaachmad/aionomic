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

import id.co.aio.procure_to_pay.aio_ptp.TblPbAttachment;

/**
 * Service object for domain model class {@link TblPbAttachment}.
 */
public interface TblPbAttachmentService {

    /**
     * Creates a new TblPbAttachment. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblPbAttachment if any.
     *
     * @param tblPbAttachment Details of the TblPbAttachment to be created; value cannot be null.
     * @return The newly created TblPbAttachment.
     */
    TblPbAttachment create(@Valid TblPbAttachment tblPbAttachment);


	/**
     * Returns TblPbAttachment by given id if exists.
     *
     * @param tblpbattachmentId The id of the TblPbAttachment to get; value cannot be null.
     * @return TblPbAttachment associated with the given tblpbattachmentId.
	 * @throws EntityNotFoundException If no TblPbAttachment is found.
     */
    TblPbAttachment getById(Integer tblpbattachmentId);

    /**
     * Find and return the TblPbAttachment by given id if exists, returns null otherwise.
     *
     * @param tblpbattachmentId The id of the TblPbAttachment to get; value cannot be null.
     * @return TblPbAttachment associated with the given tblpbattachmentId.
     */
    TblPbAttachment findById(Integer tblpbattachmentId);

	/**
     * Find and return the list of TblPbAttachments by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tblpbattachmentIds The id's of the TblPbAttachment to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblPbAttachments associated with the given tblpbattachmentIds.
     */
    List<TblPbAttachment> findByMultipleIds(List<Integer> tblpbattachmentIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblPbAttachment. It replaces all fields of the existing TblPbAttachment with the given tblPbAttachment.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblPbAttachment if any.
     *
     * @param tblPbAttachment The details of the TblPbAttachment to be updated; value cannot be null.
     * @return The updated TblPbAttachment.
     * @throws EntityNotFoundException if no TblPbAttachment is found with given input.
     */
    TblPbAttachment update(@Valid TblPbAttachment tblPbAttachment);


    /**
     * Partially updates the details of an existing TblPbAttachment. It updates only the
     * fields of the existing TblPbAttachment which are passed in the tblPbAttachmentPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblPbAttachment if any.
     *
     * @param tblpbattachmentId The id of the TblPbAttachment to be deleted; value cannot be null.
     * @param tblPbAttachmentPatch The partial data of TblPbAttachment which is supposed to be updated; value cannot be null.
     * @return The updated TblPbAttachment.
     * @throws EntityNotFoundException if no TblPbAttachment is found with given input.
     */
    TblPbAttachment partialUpdate(Integer tblpbattachmentId, Map<String, Object> tblPbAttachmentPatch);

    /**
     * Deletes an existing TblPbAttachment with the given id.
     *
     * @param tblpbattachmentId The id of the TblPbAttachment to be deleted; value cannot be null.
     * @return The deleted TblPbAttachment.
     * @throws EntityNotFoundException if no TblPbAttachment found with the given id.
     */
    TblPbAttachment delete(Integer tblpbattachmentId);

    /**
     * Deletes an existing TblPbAttachment with the given object.
     *
     * @param tblPbAttachment The instance of the TblPbAttachment to be deleted; value cannot be null.
     */
    void delete(TblPbAttachment tblPbAttachment);

    /**
     * Find all TblPbAttachments matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblPbAttachments.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblPbAttachment> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblPbAttachments matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblPbAttachments.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblPbAttachment> findAll(String query, Pageable pageable);

    /**
     * Exports all TblPbAttachments matching the given input query to the given exportType format.
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
     * Exports all TblPbAttachments matching the given input query to the given exportType format.
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
     * Retrieve the count of the TblPbAttachments in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblPbAttachment.
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