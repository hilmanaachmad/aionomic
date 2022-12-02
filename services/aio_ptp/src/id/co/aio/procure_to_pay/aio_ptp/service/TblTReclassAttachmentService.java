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

import id.co.aio.procure_to_pay.aio_ptp.TblTReclassAttachment;

/**
 * Service object for domain model class {@link TblTReclassAttachment}.
 */
public interface TblTReclassAttachmentService {

    /**
     * Creates a new TblTReclassAttachment. It does cascade insert for all the children in a single transaction.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTReclassAttachment if any.
     *
     * @param tblTreclassAttachment Details of the TblTReclassAttachment to be created; value cannot be null.
     * @return The newly created TblTReclassAttachment.
     */
    TblTReclassAttachment create(@Valid TblTReclassAttachment tblTreclassAttachment);


	/**
     * Returns TblTReclassAttachment by given id if exists.
     *
     * @param tbltreclassattachmentId The id of the TblTReclassAttachment to get; value cannot be null.
     * @return TblTReclassAttachment associated with the given tbltreclassattachmentId.
	 * @throws EntityNotFoundException If no TblTReclassAttachment is found.
     */
    TblTReclassAttachment getById(Integer tbltreclassattachmentId);

    /**
     * Find and return the TblTReclassAttachment by given id if exists, returns null otherwise.
     *
     * @param tbltreclassattachmentId The id of the TblTReclassAttachment to get; value cannot be null.
     * @return TblTReclassAttachment associated with the given tbltreclassattachmentId.
     */
    TblTReclassAttachment findById(Integer tbltreclassattachmentId);

	/**
     * Find and return the list of TblTReclassAttachments by given id's.
     *
     * If orderedReturn true, the return List is ordered and positional relative to the incoming ids.
     *
     * In case of unknown entities:
     *
     * If enabled, A null is inserted into the List at the proper position(s).
     * If disabled, the nulls are not put into the return List.
     *
     * @param tbltreclassattachmentIds The id's of the TblTReclassAttachment to get; value cannot be null.
     * @param orderedReturn Should the return List be ordered and positional in relation to the incoming ids?
     * @return TblTReclassAttachments associated with the given tbltreclassattachmentIds.
     */
    List<TblTReclassAttachment> findByMultipleIds(List<Integer> tbltreclassattachmentIds, boolean orderedReturn);


    /**
     * Updates the details of an existing TblTReclassAttachment. It replaces all fields of the existing TblTReclassAttachment with the given tblTreclassAttachment.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTReclassAttachment if any.
     *
     * @param tblTreclassAttachment The details of the TblTReclassAttachment to be updated; value cannot be null.
     * @return The updated TblTReclassAttachment.
     * @throws EntityNotFoundException if no TblTReclassAttachment is found with given input.
     */
    TblTReclassAttachment update(@Valid TblTReclassAttachment tblTreclassAttachment);


    /**
     * Partially updates the details of an existing TblTReclassAttachment. It updates only the
     * fields of the existing TblTReclassAttachment which are passed in the tblTreclassAttachmentPatch.
     *
     * This method overrides the input field values using Server side or database managed properties defined on TblTReclassAttachment if any.
     *
     * @param tbltreclassattachmentId The id of the TblTReclassAttachment to be deleted; value cannot be null.
     * @param tblTreclassAttachmentPatch The partial data of TblTReclassAttachment which is supposed to be updated; value cannot be null.
     * @return The updated TblTReclassAttachment.
     * @throws EntityNotFoundException if no TblTReclassAttachment is found with given input.
     */
    TblTReclassAttachment partialUpdate(Integer tbltreclassattachmentId, Map<String, Object> tblTreclassAttachmentPatch);

    /**
     * Deletes an existing TblTReclassAttachment with the given id.
     *
     * @param tbltreclassattachmentId The id of the TblTReclassAttachment to be deleted; value cannot be null.
     * @return The deleted TblTReclassAttachment.
     * @throws EntityNotFoundException if no TblTReclassAttachment found with the given id.
     */
    TblTReclassAttachment delete(Integer tbltreclassattachmentId);

    /**
     * Deletes an existing TblTReclassAttachment with the given object.
     *
     * @param tblTreclassAttachment The instance of the TblTReclassAttachment to be deleted; value cannot be null.
     */
    void delete(TblTReclassAttachment tblTreclassAttachment);

    /**
     * Find all TblTReclassAttachments matching the given QueryFilter(s).
     * All the QueryFilter(s) are ANDed to filter the results.
     * This method returns Paginated results.
     *
     * @deprecated Use {@link #findAll(String, Pageable)} instead.
     *
     * @param queryFilters Array of queryFilters to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTReclassAttachments.
     *
     * @see QueryFilter
     * @see Pageable
     * @see Page
     */
    @Deprecated
    Page<TblTReclassAttachment> findAll(QueryFilter[] queryFilters, Pageable pageable);

    /**
     * Find all TblTReclassAttachments matching the given input query. This method returns Paginated results.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query The query to filter the results; No filters applied if the input is null/empty.
     * @param pageable Details of the pagination information along with the sorting options. If null returns all matching records.
     * @return Paginated list of matching TblTReclassAttachments.
     *
     * @see Pageable
     * @see Page
     */
    Page<TblTReclassAttachment> findAll(String query, Pageable pageable);

    /**
     * Exports all TblTReclassAttachments matching the given input query to the given exportType format.
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
     * Exports all TblTReclassAttachments matching the given input query to the given exportType format.
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
     * Retrieve the count of the TblTReclassAttachments in the repository with matching query.
     * Note: Go through the documentation for <u>query</u> syntax.
     *
     * @param query query to filter results. No filters applied if the input is null/empty.
     * @return The count of the TblTReclassAttachment.
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